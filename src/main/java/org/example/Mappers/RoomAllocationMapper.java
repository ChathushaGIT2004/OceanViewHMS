package org.example.Mappers;

import org.example.Models.Room.RoomAllocation;
import org.example.Models.Room.Room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomAllocationMapper {

    public static RoomAllocation map(ResultSet rs) throws SQLException {

        RoomAllocation allocation = new RoomAllocation();

        allocation.setAllocationID(rs.getInt("AllocationID"));
        allocation.setReservationId(rs.getInt("ReservationID"));

        // ---- Room Mapping ----
        Room room = new Room();
        room.setRoomID(rs.getInt("RoomID"));
        allocation.setRoom(room);


        allocation.setAllocationStatus (rs.getString("AllocationStatus"));

        return allocation;
    }

    public static List<RoomAllocation> mapList(ResultSet rs) throws SQLException {

        List<RoomAllocation> list = new ArrayList<>();

        while (rs.next()) {
            list.add(map(rs));
        }

        return list;
    }
}
