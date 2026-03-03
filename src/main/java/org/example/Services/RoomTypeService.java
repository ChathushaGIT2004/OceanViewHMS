package org.example.Services;

import org.example.Models.Room.RoomType;
import org.example.dao.RoomTypeDAO;

import java.util.List;

public class RoomTypeService {

    private final RoomTypeDAO roomTypeDAO;

    public RoomTypeService(RoomTypeDAO roomTypeDAO) {
        this.roomTypeDAO = roomTypeDAO;
    }

    // Get RoomType by ID
    public RoomType getRoomTypeById(int roomTypeID) {
        return roomTypeDAO.findById(roomTypeID);
    }

    // Get all RoomTypes
    public List<RoomType> getAllRoomTypes() {
        return roomTypeDAO.findAll();
    }

    // Add new RoomType
    public void addRoomType(RoomType roomType) {
        roomTypeDAO.save(roomType);
    }

    // Update RoomType details
    public void updateRoomType(RoomType roomType) {
        roomTypeDAO.update(roomType);
    }

    // Delete RoomType by ID
    public void deleteRoomType(int roomTypeID) {
        roomTypeDAO.delete(roomTypeID);
    }
}