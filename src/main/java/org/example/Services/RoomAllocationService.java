package org.example.Services;

import org.example.DTO.ResponseMessageDTO;
import org.example.Models.Room.Room;
import org.example.Models.Room.RoomAllocation;
import org.example.dao.RoomAllocationDAO;
import org.example.dao.impl.RoomAllocationDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class RoomAllocationService {

    private final RoomAllocationDAO allocationDAO;
    private final RoomService roomService;
    private   ReservationService reservationService;

    // Constructor
    public RoomAllocationService() {
        this.allocationDAO = new RoomAllocationDAOImpl();
         this.roomService=new RoomService();


    }


    // Get allocation by ID

    public RoomAllocation getAllocationById(int allocationID) {
        return allocationDAO.getdById(allocationID);
    }

    
    // Get allocations by Reservation ID
    
    public List<RoomAllocation> getByReservationId(int reservationID) {
        return allocationDAO.getByReservationId(reservationID);
    }

    
    // Get all allocations
    
    public List<RoomAllocation> getAllAllocations() {
        return allocationDAO.getAll();
    }

    

    public ResponseMessageDTO addRoomToReservation(int reservationId, int roomId) {

        ResponseMessageDTO response=new ResponseMessageDTO();
           Room room= roomService.getRoomById(roomId);
        if (room == null || !"AVAILABLE".equals(room.getRoomStatus())) {
            throw new IllegalArgumentException("Room is not available");
        }

        RoomAllocation allocation = new RoomAllocation();
        allocation.setReservationId(reservationId);
        allocation.setRoom(room);
        allocation.setAllocationStatus("RESERVED");

        System.out.println("Allocation Started"+allocation.getAllocationID());
        allocationDAO.save(allocation);


        // Update room status
        room.setRoomStatus("RESERVED");
          roomService.updateRoomStatus(room);

          reservationService =new ReservationService();
          double value= reservationService.calculateReservationTotal(reservationId);

          System.out.println("Allocation value"+value);
          response.setSuccess(true);
          response.setMessage("Added ");
        return  response;
    }

    public boolean removeRoomFromReservation(int allocationId,Boolean calculate) {
       System.out.println("allocation ID is :"+allocationId);
        RoomAllocation allocation = allocationDAO.getdById(allocationId);

        System.out.println("Allocation fount at service:"+allocation.getAllocationID());
        if (allocation == null) return false;
        int reservationID= allocation.getReservationId();

        Room room = allocation.getRoom();
        if (room != null) {
            System.out.println("Room found room id "+room.getRoomID());
            room.setRoomStatus("AVAILABLE");
           roomService.updateRoomStatus(room);
        }

        allocationDAO.delete(allocationId);
        if (calculate){
            reservationService =new ReservationService();
            reservationService.calculateReservationTotal(reservationID);
        }


        return true;
    }

    public void updateAllocation(RoomAllocation allocation) {
        allocationDAO.update(allocation);
    }


}


