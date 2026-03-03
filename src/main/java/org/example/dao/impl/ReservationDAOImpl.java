package org.example.dao.impl;

import org.example.DTO.ResponseMessageDTO;
import org.example.Util.DBConnection;
import org.example.dao.ReservationDAO;
import org.example.Mappers.ReservationMapper;
import org.example.Mappers.RoomAllocationMapper;
import org.example.Models.Billings.BillableItems.Reservation;
import org.example.Models.Room.RoomAllocation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {

    private   Connection conn;

    public ReservationDAOImpl() {
        try {
            this.conn = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==============================
    // Get reservation by ID
    // ==============================
    @Override
    public Reservation getById(int reservationID) {
        String sql = "SELECT * FROM Reservations AS r JOIN guests AS g ON g.GuestID=r.GuestID  WHERE ReservationID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, reservationID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Reservation reservation = ReservationMapper.map(rs);
                reservation.setRoomAllocationList(getAllocations(reservationID));
                return reservation;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ==============================
    // Get reservations by guest ID
    // ==============================
    @Override
    public List<Reservation> getByGuestId(int guestID) {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM Reservations WHERE GuestID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, guestID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reservation reservation = ReservationMapper.map(rs);
                reservation.setRoomAllocationList(getAllocations(reservation.getReservationID()));
                list.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ==============================
    // Get all reservations
    // ==============================
    @Override
    public List<Reservation> getAll() {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM Reservations AS r JOIN guests as g ON g.GuestID=r.GuestID ORDER BY ReservationDate DESC;";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reservation reservation = ReservationMapper.map(rs);
                reservation.setRoomAllocationList(getAllocations(reservation.getReservationID()));
                list.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ==============================
    // Get latest reservations
    // ==============================
    @Override
    public List<Reservation> getLatest(int limit) {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM Reservations ORDER BY ReservationDate DESC LIMIT ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reservation reservation = ReservationMapper.map(rs);
                reservation.setRoomAllocationList(getAllocations(reservation.getReservationID()));
                list.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ==============================
    // Get reservations by time frame
    // ==============================
    @Override
    public List<Reservation> getByTimeFrame(Timestamp from, Timestamp to, int limit) {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM Reservations WHERE ReservationDate BETWEEN ? AND ? " +
                "ORDER BY ReservationDate DESC LIMIT ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, from);
            ps.setTimestamp(2, to);
            ps.setInt(3, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reservation reservation = ReservationMapper.map(rs);
                reservation.setRoomAllocationList(getAllocations(reservation.getReservationID()));
                list.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ==============================
    // Save reservation with allocations
    // ==============================
    @Override
    public ResponseMessageDTO save(Reservation reservation) {

        ResponseMessageDTO responseMessageDTO=new ResponseMessageDTO();
        System.out.println("Reservatin DaO method Accessed");
        String sql = "INSERT INTO Reservations (GuestID,   CheckInDate,CheckOutDate,NumberOfGuests,TotalAmount, Status) VALUES (?,?,?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, reservation.getGuest().getGuestID());
            ps.setDate(2,  reservation.getCheckInDate());
            ps.setDate(3,reservation.getCheckOutDate());
            ps.setInt(4,reservation.getNumberOfGuests());
            ps.setDouble(5, reservation.getTotalAmount());
            ps.setString(6, reservation.getStatus());
            ps.executeUpdate();

            // Get generated ReservationID
            ResultSet keys = ps.getGeneratedKeys();
            System.out.println("keys:" +keys);
            if (keys.next()) {

                int reservationID = keys.getInt(1);
                responseMessageDTO.setSuccess(true);
                responseMessageDTO.setMessage("reservation ID Obtained");
                responseMessageDTO.setData(reservationID);




                return responseMessageDTO;

            }
            else{
                responseMessageDTO.setSuccess(false);
                responseMessageDTO.setMessage("Database Insertion Failed");
                System.out.println("Database Adding Failed");
            }
        } catch (SQLException e) {
            responseMessageDTO.setSuccess(false);
            responseMessageDTO.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return responseMessageDTO;
    }

    // ==============================
    // Update reservation
    // ==============================
    @Override
    public ResponseMessageDTO update(Reservation reservation) {
        ResponseMessageDTO response = new ResponseMessageDTO();
        System.out.println("Reservation Update Method Accessed");

        String sql = "UPDATE Reservations SET GuestID=?, TotalAmount=?, Status=? WHERE ReservationID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, reservation.getGuest().getGuestID());
            ps.setDouble(2, reservation.getTotalAmount());
            ps.setString(3, reservation.getStatus());
            ps.setInt(4, reservation.getReservationID());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                response.setSuccess(true);
                response.setMessage("Reservation updated successfully. Rows affected: " + rowsAffected);
                System.out.println("Update executed. Total is " + reservation.getTotalAmount());
            } else {
                response.setSuccess(false);
                response.setMessage("No reservation updated. Check ReservationID: " + reservation.getReservationID());
                System.out.println("Update skipped. No matching reservation found.");
            }

        } catch (SQLException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }

        return response;
    }
    // ==============================
    // Delete reservation
    // ==============================
    @Override
    public void delete(int reservationID) {
        String sql = "DELETE FROM Reservations WHERE ReservationID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, reservationID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==============================
    // PRIVATE HELPERS FOR ROOM ALLOCATIONS
    // ==============================
    private List<RoomAllocation> getAllocations(int reservationID) {
        List<RoomAllocation> list = new ArrayList<>();
        String sql = "SELECT * FROM RoomAllocation WHERE ReservationID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, reservationID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(RoomAllocationMapper.map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void saveAllocations(int reservationID, List<RoomAllocation> allocations) {
        if (allocations == null || allocations.isEmpty()) return;

        String sql = "INSERT INTO RoomAllocation (ReservationID, RoomID,   AllocationStatus) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (RoomAllocation alloc : allocations) {
                ps.setInt(1, reservationID);
                ps.setInt(2, alloc.getRoom().getRoomID());
                ps.setString(5, alloc.getAllocationStatus());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
