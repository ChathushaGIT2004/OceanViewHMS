package org.example.dao.impl;

import org.example.Config.DBConnection;
import org.example.Mappers.GuestMapper;
import org.example.Models.Guest;
import org.example.dao.GuestDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                return GuestMapper.map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    @Override
    public List<Guest> findAll() {
        List<Guest> guests = new ArrayList<>();
        String sql = "SELECT * FROM Guests";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                guests.add(GuestMapper.map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guests;
    }

    @Override
    public void save(Guest guest) {
        String sql = "INSERT INTO Guests (FullName, NIC, ContactNumber, Email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, guest.getFullName());
            ps.setString(2, guest.getNIC());
            ps.setString(3, guest.getContactNumber());
            ps.setString(4, guest.getEmail());
            ps.executeUpdate();

            // Set generated GuestID back to object
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                guest.setGuestID(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Guest guest) {
        String sql = "UPDATE Guests SET FullName=?, NIC=?, ContactNumber=?, Email=? WHERE GuestID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, guest.getFullName());
            ps.setString(2, guest.getNIC());
            ps.setString(3, guest.getContactNumber());
            ps.setString(4, guest.getEmail());
            ps.setInt(5, guest.getGuestID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int guestID) {
        String sql = "DELETE FROM Guests WHERE GuestID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, guestID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}