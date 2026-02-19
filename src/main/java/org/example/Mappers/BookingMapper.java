package org.example.Mappers;

import org.example.Models.Booking;
import org.example.Models.Reservation;
import org.example.Models.Room;
import org.example.dao.ReservationDAO;
import org.example.dao.RoomDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingMapper {


    public static Booking map(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingID(rs.getInt("BookingID"));

        // Fetch Reservation object using ReservationID
        int reservationID = rs.getInt("ReservationID");
        Reservation reservation = ReservationDAO.getReservationById(reservationID); // You need this DAO method
        booking.setReservation(reservation);

        // Fetch Room object using RoomID
        int roomID = rs.getInt("RoomID");
        Room room = RoomDAO.getRoomById(roomID); // You need this DAO method
        booking.setRoom(room);

        // Convert SQL DATE to LocalDate
        java.sql.Date checkIn = rs.getDate("CheckInDate");
        if (checkIn != null) {
            booking.setCheckInDate(checkIn.toLocalDate());
        }

        java.sql.Date checkOut = rs.getDate("CheckOutDate");
        if (checkOut != null) {
            booking.setCheckOutDate(checkOut.toLocalDate());
        }

        booking.setBookingStatus(rs.getString("BookingStatus"));

        return booking;
    }


    public static List<Booking> mapList(ResultSet rs) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        while (rs.next()) {
            bookings.add(map(rs));
        }
        return bookings;
    }
}
