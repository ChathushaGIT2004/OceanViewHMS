package org.example.Mappers;




import org.example.Models.Guest;
import org.example.Models.Reservation;
import org.example.dao.GuestDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationMapper {


    public static Reservation map(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservationID(rs.getInt("ReservationID"));

        // Fetch Guest object using GuestID
        int guestID = rs.getInt("GuestID");
        Guest guest = GuestDAO.findById (guestID); // You need this DAO method
        reservation.setGuest(guest);

        // Convert SQL Timestamp to LocalDateTime
        java.sql.Timestamp timestamp = rs.getTimestamp("ReservationDate");
        if (timestamp != null) {
            reservation.setReservationDate(timestamp.toLocalDateTime());
        }

        reservation.setTotalAmount(rs.getDouble("TotalAmount"));
        reservation.setStatus(rs.getString("Status"));

        return reservation;
    }


    public static List<Reservation> mapList(ResultSet rs) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        while (rs.next()) {
            reservations.add(map(rs));
        }
        return reservations;
    }
}
