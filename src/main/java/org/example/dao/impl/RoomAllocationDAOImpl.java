package org.example.dao.impl;

import org.example.Util.DBConnection;
import org.example.dao.RoomAllocationDAO;
import org.example.Mappers.RoomAllocationMapper;
import org.example.Models.Room.RoomAllocation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomAllocationDAOImpl implements RoomAllocationDAO {

    private  Connection conn=null;

    public RoomAllocationDAOImpl( ) {
        try {
            this.conn = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                (ReservationID, RoomID,  AllocationStatus)
                VALUES (?, ?, ?)
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, allocation.getReservationId());
            ps.setInt(2, allocation.getRoom().getRoomID());
            ps.setString(3, allocation.getAllocationStatus());
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
                SET RoomID=?,  AllocationStatus=?
                WHERE AllocationID=?
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, allocation.getRoom().getRoomID());
            ps.setString(2, allocation.getAllocationStatus());
            ps.setInt(3, allocation.getAllocationID());
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
