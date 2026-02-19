package org.example.dao.impl;

import org.example.dao.ReservationDAO;
import org.example.Models.Reservation;
import org.example.Config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReservationDAOImpl implements ReservationDAO {

    @Override
    public void addReservation(Reservation r) {

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "INSERT INTO reservations(reservation_id, guest_id, room_id, checkin, checkout) VALUES(?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setInt(1, r.getReservationId());
            pst.setInt(2, r.getGuest().getGuestId());
            pst.setInt(3, r.getRoom().getRoomId());
            pst.setDate(4, java.sql.Date.valueOf(r.getCheckInDate()));
            pst.setDate(5, java.sql.Date.valueOf(r.getCheckOutDate()));

            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reservation getReservation(int reservationId) {

        return null;
    }
}
