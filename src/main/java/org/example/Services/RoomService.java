package org.example.Services;

import org.example.Models.Room.Room;
import org.example.Util.DBConnection;
import org.example.dao.RoomDAO;
import org.example.dao.impl.RoomDAOImpl; // Make sure you have an implementation

import java.sql.SQLException;
import java.util.List;

public class RoomService {

    private final RoomDAO roomDAO;


    public RoomService()  {
        this.roomDAO = new RoomDAOImpl();
    }

    // Get a room by ID
    public Room getRoomById(int roomID) {
        return roomDAO.findById(roomID);
    }

    // Get all rooms
    public List<Room> getAllRooms() {
        return roomDAO.findAll();
    }

    // Get available rooms only
    public List<Room> getAvailableRooms() {
        return roomDAO.findAvailableRooms();
    }

    // Add a new room
    public void addRoom(Room room) {
        roomDAO.save(room);
    }

    // Update room details
    public void updateRoom(Room room) {
        roomDAO.update(room);
    }

    // Delete a room by ID
    public void deleteRoom(int roomID) {
        roomDAO.delete(roomID);
    }

    // Find rooms by type
    public List<Room> getRoomsByType(int roomTypeID) {
        return roomDAO.findByRoomType(roomTypeID);
    }

    // Find rooms by status (Available, Occupied, Maintenance)
    public List<Room> getRoomsByStatus(String status) {
        return roomDAO.findByStatus(status);
    }
}