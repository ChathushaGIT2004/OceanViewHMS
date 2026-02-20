package org.example.Mappers;

import org.example.Models.Room.Room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomMapper {

    // Map single Room (store only roomTypeID in Room class if you want)
    public static Room map(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setRoomID(rs.getInt("RoomID"));
        room.setRoomType(rs.getInt("RoomTypeID"));
        room.setRoomStatus(rs.getString("RoomStatus"));
        return room;
    }

    // Map list of rooms
    public static List<Room> mapList(ResultSet rs) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        while (rs.next()) {
            rooms.add(map(rs));
        }
        return rooms;
    }


    }

