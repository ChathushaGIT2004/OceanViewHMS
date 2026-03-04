package org.example.dao.impl;


import org.example.Mappers.RoomMapper;
import org.example.Models.Room.Room;
import org.example.dao.RoomDAO;

import java.util.List;

import org.example.Util.DBConnection;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDAOImpl implements RoomDAO {

    private Connection conn;

    public RoomDAOImpl() {
        try {
            this.conn = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Room findById(int roomID) {
        System.out.println("Accessed RoomDAO method");
        String sql = "SELECT * FROM Rooms WHERE RoomID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return RoomMapper.map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Room> findAll() {
        String sql = "SELECT * FROM Rooms";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            return RoomMapper.mapList(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Room> findAvailableRooms() {
        String sql = "SELECT * FROM rooms WHERE RoomStatus='AVAILABLE' ";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            return RoomMapper.mapList(rs); // Mapper handles ResultSet -> List<Room>

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Room room) {
        String sql = "INSERT INTO Rooms(RoomID, RoomTypeID, RoomStatus) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, room.getRoomID());
            ps.setInt(2, room.getRoomType());
            ps.setString(3, room.getRoomStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Room room) {
        String sql = "UPDATE Rooms SET RoomTypeID=?, RoomStatus=? WHERE RoomID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, room.getRoomType());
            ps.setString(2, room.getRoomStatus());
            ps.setInt(3, room.getRoomID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStatus(Room room) {
        String sql = "UPDATE Rooms SET   RoomStatus=? WHERE RoomID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, room.getRoomStatus());
            ps.setInt(2, room.getRoomID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int roomID) {
        String sql = "DELETE FROM Rooms WHERE RoomID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Room> findByRoomType(int roomTypeID) {
        String sql = "SELECT * FROM Rooms WHERE RoomTypeID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomTypeID);
            ResultSet rs = ps.executeQuery();
            return RoomMapper.mapList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Room> findByStatus(String status) {
        String sql = "SELECT * FROM Rooms WHERE RoomStatus = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            return RoomMapper.mapList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
