package org.example.dao.impl;

import org.example.dao.RoomDAO;
import org.example.Models.Room;
import org.example.Config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RoomDAOImpl implements RoomDAO {

    @Override
    public Room getRoomByType(String type) {

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "SELECT * FROM rooms WHERE type = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, type);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Room(
                        rs.getInt("room_id"),
                        rs.getString("type"),
                        rs.getDouble("price")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
