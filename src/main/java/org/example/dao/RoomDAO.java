package org.example.dao;

import org.example.Models.Room.Room;

import java.util.List;


public interface RoomDAO {
    Room findById(int roomID);
    List<Room> findAll();
    List<Room> findAvailableRooms();
    void save(Room room);
    void update(Room room);
    void delete(int roomID);

    List<Room> findByRoomType(int roomTypeID);

    List<Room> findByStatus(String status);
}
