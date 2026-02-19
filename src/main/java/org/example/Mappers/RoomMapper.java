package org.example.Mappers;




import org.example.Models.Room;
import org.example.Models.RoomType;
import org.example.dao.RoomTypeDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomMapper {

    /**
     * Map a single ResultSet row to a Room object.
     * Assumes you already have a RoomType object available (you can fetch it separately)
     */
    public static Room map(ResultSet rs, RoomType roomType) throws SQLException {
        Room room = new Room();
        room.setRoomID(rs.getInt("RoomID"));
        room.setRoomType(roomType); // Set RoomType object
        room.setRoomStatus(rs.getString("RoomStatus"));
        return room;
    }


    public static List<Room> mapList(ResultSet rs) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        while (rs.next()) {
            // Fetch RoomType object using RoomTypeID
            int roomTypeID = rs.getInt("RoomTypeID");
            RoomType roomType = RoomTypeDAO.findById (roomTypeID); // You need this DAO method
            rooms.add(map(rs, roomType));
        }
        return rooms;
    }
}
