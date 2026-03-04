package org.example.dao.impl;

import org.example.Models.Room.RoomType;
import org.example.dao.RoomTypeDAO;
import org.example.Mappers.RoomTypeMapper;
import org.example.Util.DBConnection;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class RoomTypeDAOImpl implements RoomTypeDAO {

    @Override
    public RoomType findById(int roomTypeID) {

        String sql = "SELECT * FROM RoomType WHERE RoomTypeID = ?";

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, roomTypeID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Room type fetched");
                RoomType roomType=RoomTypeMapper.map(rs);

                return RoomTypeMapper.map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<RoomType> findAll() {
        String sql = "SELECT * FROM RoomType";

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            return RoomTypeMapper.mapList(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public void save(RoomType roomType) {
        String sql = "INSERT INTO RoomType (RoomTypeID, TypeName, ChargePerNight, OccupancyLimit) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, roomType.getRoomTypeID());
            stmt.setString(2, roomType.getTypeName());
            stmt.setDouble(3, roomType.getChargePerNight());
            stmt.setInt(4, roomType.getOccupancyLimit());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(RoomType roomType) {
        String sql = "UPDATE RoomType SET TypeName = ?, ChargePerNight = ?, OccupancyLimit = ? WHERE RoomTypeID = ?";

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, roomType.getTypeName());
            stmt.setDouble(2, roomType.getChargePerNight());
            stmt.setInt(3, roomType.getOccupancyLimit());
            stmt.setInt(4, roomType.getRoomTypeID());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int roomTypeID) {
        String sql = "DELETE FROM RoomType WHERE RoomTypeID = ?";

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, roomTypeID);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}