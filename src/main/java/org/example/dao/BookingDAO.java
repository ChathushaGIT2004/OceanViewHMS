package org.example.dao;

import org.example.Models.Booking;
import java.util.List;


public interface BookingDAO {
    Booking findById(int bookingID);
    List<Booking> findByReservationId(int reservationID);
    List<Booking> findAll();
    void save(Booking booking);
    void update(Booking booking);
    void delete(int bookingID);
}
