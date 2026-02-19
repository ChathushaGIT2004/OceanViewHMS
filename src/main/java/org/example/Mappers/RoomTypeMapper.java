package org.example.Mappers;

import org.example.Models.RoomType;

import java.sql.SQLException;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeMapper {

    public static RoomType map(ResultSet rs) throws SQLException {
        RoomType roomType = new RoomType();
        roomType.setRoomTypeID(rs.getInt("RoomTypeID"));
        roomType.setTypeName(rs.getString("TypeName"));
        roomType.setChargePerNight(rs.getDouble("ChargePerNight"));
        roomType.setOccupancyLimit(rs.getInt("OccupancyLimit"));
        return roomType;
    }

    public static List<RoomType> mapList(ResultSet rs) throws SQLException {
        List<RoomType> roomTypes = new ArrayList<>();
        while (rs.next()) {
            roomTypes.add(map(rs));
        }
        return roomTypes;
    }
}
