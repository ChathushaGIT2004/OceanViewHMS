package org.example.dao.impl;

import org.example.dao.RoomAllocationDAO;
import org.example.Mappers.RoomAllocationMapper;
import org.example.Models.Room.RoomAllocation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomAllocationDAOImpl implements RoomAllocationDAO {

    private final Connection conn;

    public RoomAllocationDAOImpl(Connection conn) {
        this.conn = conn;
    }

    // ==============================
    // Get allocation by AllocationID
    // ==============================
    @Override
    public RoomAllocation getdById(int allocationID) {
        String sql = "SELECT * FROM RoomAllocation WHERE AllocationID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, allocationID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return RoomAllocationMapper.map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ==============================
    // Get allocations by ReservationID
    // ==============================
    @Override
    public List<RoomAllocation> getByReservationId(int reservationID) {
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

    // ==============================
    // Get all allocations
    // ==============================
    @Override
    public List<RoomAllocation> getAll() {
        List<RoomAllocation> list = new ArrayList<>();
        String sql = "SELECT * FROM RoomAllocation";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(RoomAllocationMapper.map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ==============================
    // Save allocation
    // ==============================
    @Override
    public void save(RoomAllocation allocation) {
        String sql = """
                INSERT INTO RoomAllocation
                (ReservationID, RoomID, CheckInDate, CheckOutDate, AllocationStatus)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, allocation.getReservationId());
            ps.setInt(2, allocation.getRoom().getRoomID());
            ps.setDate(3, Date.valueOf(allocation.getCheckInDate()));
            ps.setDate(4, Date.valueOf(allocation.getCheckOutDate()));
            ps.setString(5, allocation.getAllocationStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==============================
    // Update allocation
    // ==============================
    @Override
    public void update(RoomAllocation allocation) {
        String sql = """
                UPDATE RoomAllocation
                SET RoomID=?, CheckInDate=?, CheckOutDate=?, AllocationStatus=?
                WHERE AllocationID=?
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, allocation.getRoom().getRoomID());
            ps.setDate(2, Date.valueOf(allocation.getCheckInDate()));
            ps.setDate(3, Date.valueOf(allocation.getCheckOutDate()));
            ps.setString(4, allocation.getAllocationStatus());
            ps.setInt(5, allocation.getAllocationID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==============================
    // Delete allocation
    // ==============================
    @Override
    public void delete(int allocationID) {
        String sql = "DELETE FROM RoomAllocation WHERE AllocationID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, allocationID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
