package org.example.dao.impl;

import org.example.dao.GuestDAO;
import org.example.Models.Guest;
import org.example.Config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GuestDAOImpl implements GuestDAO {

    @Override
    public void addGuest(Guest guest) {

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "INSERT INTO guests(name, address, contact) VALUES(?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, guest.getName());
            pst.setString(2, guest.getAddress());
            pst.setString(3, guest.getContactNumber());

            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Guest getGuestById(int guestId) {

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "SELECT * FROM guests WHERE guest_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, guestId);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Guest(
                        rs.getInt("guest_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("contact")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
