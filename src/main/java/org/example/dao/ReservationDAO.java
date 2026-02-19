package org.example.dao;

import org.example.Models.Reservation;


import java.util.List;


public interface ReservationDAO {
    Reservation findById(int reservationID);
    List<Reservation> findByGuestId(int guestID);
    List<Reservation> findAll();
    void save(Reservation reservation);
    void update(Reservation reservation);
    void delete(int reservationID);
}
