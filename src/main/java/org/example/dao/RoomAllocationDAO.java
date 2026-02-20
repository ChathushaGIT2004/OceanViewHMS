package org.example.dao;

import org.example.Models.Room.RoomAllocation;

import java.util.List;


public  interface RoomAllocationDAO {
    RoomAllocation getdById(int AllocationID);
    List<RoomAllocation> getByReservationId(int reservationID);
    List<RoomAllocation> getAll();
    void save(RoomAllocation roomAllocation);
    void update(RoomAllocation roomAllocation);
    void delete(int bookingID);
}
