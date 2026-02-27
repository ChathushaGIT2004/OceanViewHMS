package org.example.dao.impl;

import java.util.List;

import org.example.Mappers.GuestMapper;
import org.example.Models.Guest;
import org.example.dao.GuestDAO;
import org.example.Util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuestDAOImpl implements GuestDAO {

    private Connection conn;

    public GuestDAOImpl() {
        try {
            conn = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Guest findById(int guestID) {
        String sql = "SELECT * FROM Guests WHERE GuestID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, guestID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return  GuestMapper.map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Guest> findAll() {
        return List.of();
    }

    @Override
    public void save(Guest guest) {

    }

    @Override
    public void update(Guest guest) {

    }

    @Override
    public void delete(int guestID) {

    }

    @Override
    public Guest findByNIC(String nic) {
        String sql = "SELECT * FROM Guests WHERE NIC = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nic);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return GuestMapper.map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}