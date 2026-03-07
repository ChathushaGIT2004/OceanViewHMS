package org.example.Services;

import org.example.DTO.AddReservationRequestDTO;
import org.example.DTO.ReservationDTO;
import org.example.DTO.ResponseMessageDTO;
import org.example.DTO.UpdateStatusDTO;
import org.example.Models.Billings.BillableItems.Reservation;
import org.example.Models.Guest;
import org.example.Models.Room.Room;
import org.example.Models.Room.RoomAllocation;
import org.example.Models.Room.RoomType;
import org.example.Models.Session;
import org.example.Models.User.UserActivityLog;
import org.example.Util.EmailUtil;
import org.example.Util.SessionManager;
import org.example.dao.ReservationDAO;
import org.example.dao.RoomTypeDAO;
import org.example.dao.impl.ReservationDAOImpl;
import org.example.dao.impl.RoomTypeDAOImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReservationService {

    private final ReservationDAO reservationDAO;
    private final RoomTypeDAO roomTypeDAO;
    private final RoomAllocationService allocationService;
    private final RoomService roomService;
    private final EmailUtil emailUtil=new EmailUtil();
    private  final GuestService guestService=new GuestService();

    public ReservationService() {
        try {
            Connection conn = org.example.Util.DBConnection.getInstance().getConnection();

            this.reservationDAO = new ReservationDAOImpl();
            this.roomTypeDAO = new RoomTypeDAOImpl();
            this.allocationService = new RoomAllocationService();
            this.roomService = new RoomService();

        } catch (Exception e) {
            throw new RuntimeException("DB Connection Failed", e);
        }
    }

    
    //  ADD RESERVATION + ROOM ALLOCATION

    public ResponseMessageDTO addReservation(AddReservationRequestDTO dto) {

        // Validate token first
        if (!SessionManager.isValidToken(dto.getToken())) {
            return ResponseMessageDTO.invalidToken();
        }

        // Fetch guest
        Guest guest = new GuestService().findGuestByID(dto.getGuestID());

        if (guest == null) {
            ResponseMessageDTO response = new ResponseMessageDTO();
            response.setSuccess(false);
            response.setMessage("Guest not found");
            return response;
        }

        // Map DTO -> Reservation
        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setCheckInDate(new java.sql.Date(dto.getCheckIn().getTime()));
        reservation.setCheckOutDate(new java.sql.Date(dto.getCheckOut().getTime()));
        reservation.setNumberOfGuests(dto.getNumberOfGuests());
        reservation.setStatus(dto.getStatus());

        double totalAmount = 0;

        // Save reservation to DB
        ResponseMessageDTO response = reservationDAO.save(reservation);
        int reservationId = (Integer) response.getData();
        reservation.setReservationID(reservationId);

        List<RoomAllocation> allocations = new ArrayList<>();

        // Allocate rooms
        for (Integer roomId : dto.getRoomIds()) {
            Room room = roomService.getRoomById(roomId);
            if (room == null) continue;

            RoomType type = roomTypeDAO.findById(room.getRoomType());
            double roomCharge = reservation.getNumberofNights() * type.getChargePerNight();
            totalAmount += roomCharge;

            RoomAllocation allocation = new RoomAllocation();
            allocation.setReservationId(reservationId);
            allocation.setRoom(room);
            allocation.setAllocationStatus("RESERVED");

            allocationService.addRoomToReservation(reservationId,allocation.getRoom().getRoomID());

            room.setRoomStatus("RESERVED");
            roomService.updateRoomStatus(room);

            allocations.add(allocation);
        }

        reservation.setTotalAmount(totalAmount);
        reservation.setRoomAllocationList(allocations);
        reservationDAO.update(reservation);

        String subject = "Reservation Confirmation - Ocean View Hotel";

        StringBuilder rooms = new StringBuilder();

        reservation.getRoomAllocationList().forEach(room -> {
            rooms.append("Room No: ").append(room.getRoom().getRoomID()).append("\n");
        });




        String body = String.format(
                "Dear %s,\n\n" +
                        "We are delighted to confirm your reservation at Ocean View Hotel.\n\n" +
                        "Reservation Details:\n" +
                        "Guest Name: %s\n" +
                        "Reservation ID: %s\n" +
                        "Check-in Date: %s\n" +
                        "Check-out Date: %s\n" +
                        "Room Numbers : %s\n" +
                        "Number of Guests: %d\n\n" +
                        "Room Allocation Info\n"+rooms.toString()+
                        "Thank you for choosing Ocean View Hotel. We look forward to providing you with a comfortable and memorable stay.\n\n" +
                        "Best regards,\n" +
                        "Ocean View Hotel",
                guest.getFullName(),           // Guest name
                guest.getFullName(),
                reservation.getReservationID(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate(),
                reservation.getRoomAllocationList(),
                reservation.getNumberOfGuests()
        );

        emailUtil.sendPlainTextEmail(guest.getEmail(), subject, body);

        // Log activity
        UserActivityLogService.getInstance()
                .log(dto.getToken(), "CREATE", "RESERVATION", reservationId, "Reservation Added Successfully");

        return response;
    }
    
    //  GET ALL RESERVATIONS 
    
    public List<ReservationDTO> getAllReservations() {

        List<Reservation> reservations = reservationDAO.getAll();
        List<ReservationDTO> dtoList = new ArrayList<>();

        for (Reservation res : reservations) {

            ReservationDTO dto = new ReservationDTO();

            dto.setReservationID(res.getReservationID());
            dto.setGuestID(res.getGuest().getGuestID());
            dto.setGuestFullname(res.getGuest().getFullName());
            dto.setStatus(res.getStatus());
            dto.setTotalAmount(res.getTotalAmount());
            dto.setCheckIn(res.getCheckInDate());
            dto.setCheckOut(res.getCheckOutDate());
            dto.setNumberOFGuests(res.getNumberOfGuests());

            List<RoomAllocation> allocations =
                    allocationService.getByReservationId(res.getReservationID());

            List<Integer> roomIds = new ArrayList<>();
            for (RoomAllocation alloc : allocations)
                roomIds.add(alloc.getRoom().getRoomID());

            dto.setRoomIds(roomIds);
            dtoList.add(dto);
        }

        return dtoList;
    }

    
    //  GET RESERVATION BY ID

    public Reservation getReservationById(int reservationID) {

        // Fetch the reservation
        Reservation reservation = reservationDAO.getById(reservationID);
        if (reservation == null) return null;

        // Fetch guest info (assuming lazy loading might be used)
        Guest guest = reservation.getGuest();

        // Fetch room allocations
        List<RoomAllocation> allocations = allocationService.getByReservationId(reservationID);
        List<RoomAllocation> fullAllocations = new ArrayList<>();

        for (RoomAllocation alloc : allocations) {
            // Fetch full room info
            Room room = roomService.getRoomById(alloc.getRoom().getRoomID());

            if (room != null) {
                // Fetch room type details
                RoomType type = roomTypeDAO.findById(room.getRoomType());
                room.setRoomTypeOB(type);

                // Assign room to allocation
                alloc.setRoom(room);
            }

            fullAllocations.add(alloc);
        }

        reservation.setRoomAllocationList(fullAllocations);

        return reservation;
    }

    
    //  GET RESERVATIONS BY GUEST
    
    public List<Reservation> getReservationsByGuest(int guestID) {
        return reservationDAO.getByGuestId(guestID);
    }

    
   // UPDATE RESERVATION
    
    public ResponseMessageDTO updateReservationDetails(String token, Reservation reservation) {
        if (!SessionManager.isValidToken(token)) {
            return ResponseMessageDTO.invalidToken();
        }

         
        Reservation existing = reservationDAO.getById(reservation.getReservationID());

        if (existing == null) {
            return failureResponse("Reservation not found");
        }

        
        existing.setGuest(reservation.getGuest());
        existing.setStatus(reservation.getStatus());
        existing.setTotalAmount(reservation.getTotalAmount());

        UserActivityLogService.getInstance().log(token,"UPDATE","RESERVATION", reservation.getId(),"Reservation Status Updated Successfully ");
        return reservationDAO.update(existing);
    }

     
    //  UPDATE STATUS ONLY
    public ResponseMessageDTO updateReservationStatus(UpdateStatusDTO dto) {

        if (!SessionManager.isValidToken(dto.getToken())) {
            return ResponseMessageDTO.invalidToken();
        }

        // Fetch the existing reservation
        Reservation existing = reservationDAO.getById(dto.getID());
        if (existing == null) {
            ResponseMessageDTO response = new ResponseMessageDTO();
            response.setSuccess(false);
            response.setMessage("Reservation not found");
            return response;
        }
        System.out.println("Exsisting reservation found "+dto.getStatus());

        if ("cancelled".equals(dto.getStatus())){
            List<RoomAllocation> allocations =
                    allocationService.getByReservationId(dto.getID());

            for (RoomAllocation alloc : allocations) {

                Room room = alloc.getRoom();
                room.setRoomStatus("AVAILABLE");
                roomService.updateRoomStatus(room);

                allocationService.removeRoomFromReservation(alloc.getAllocationID(),false);
            }


        }

        // Update only the status
        existing.setStatus(dto.getStatus());

        // Update in database
        ResponseMessageDTO result = reservationDAO.update(existing);

        // Log activity
        UserActivityLogService.getInstance()
                .log(dto.getToken(), "UPDATE", "RESERVATION", dto.getID(), "Reservation status updated to " + dto.getStatus());

        return result;
    }
    
    //  DELETE RESERVATION + FREE ROOMS
    
    public boolean deleteReservation(String token, int reservationID) {

        Reservation reserve= reservationDAO.getById(reservationID);
        Guest guest = guestService.findGuestByID(reserve.getGuest().getGuestID());
        List<RoomAllocation> allocations =
                allocationService.getByReservationId(reservationID);

        for (RoomAllocation alloc : allocations) {

            Room room = alloc.getRoom();
            room.setRoomStatus("AVAILABLE");
            roomService.updateRoomStatus(room);

            allocationService.removeRoomFromReservation(alloc.getAllocationID(),false);
        }

        reservationDAO.delete(reservationID);
        String message = String.format(
                "Dear %s,\n\n" +
                        "We regret to inform you that your reserved room(s) at Ocean View Hotel have been cancelled.\n\n" +
                        "You will receive any applicable refunds as per our policy.\n\n" +
                        "We apologize for the inconvenience and hope to serve you in the future.\n\n" +
                        "Best regards,\n" +
                        "Ocean View Hotel Team",
                guest.getFullName()
        );

        emailUtil.sendPlainTextEmail(guest.getEmail(), "Room Cancellation Notice - Ocean View Hotel", message);

        return true;
    }

    public double calculateReservationTotal(int reservationId) {
        System.out.println("Calculate the Reservation TOtal Accessed");
        Reservation reservation = reservationDAO.getById(reservationId);
        if (reservation == null) return 0;

        double total = 0;
        List<RoomAllocation> allocations = allocationService.getByReservationId(reservationId);

        for (RoomAllocation alloc : allocations) {
            Room room = roomService.getRoomById(alloc.getRoom().getRoomID());
            System.out.println("Room Model =\nRoomID "+room.getRoomID()+"\nRoom Type : "+room.getRoomType()+"\nRoom Status : "+room.getRoomStatus());
            RoomType type = roomTypeDAO.findById(room.getRoomType());
            System.out.println("Roomtype Fetch:"+type.getRoomTypeID());
            total += reservation.getNumberofNights() * type.getChargePerNight();

            System.out.println("Calculated"+total);
        }

        reservation.setTotalAmount(total);
        reservationDAO.update(reservation);

        return total;
    }

    private ResponseMessageDTO failureResponse(String message) {
        ResponseMessageDTO dto = new ResponseMessageDTO();
        dto.setSuccess(false);
        dto.setMessage(message);
        return dto;
    }
}