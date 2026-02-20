package org.example.dao;

import org.example.Models.Reservation;

import java.sql.Timestamp;
import java.util.List;

public interface ReservationDAO {


    Reservation getById(int reservationID);

    List<Reservation> getByGuestId(int guestID);

    List<Reservation> getAll();

    List<Reservation> getLatest(int limit);

    List<Reservation> getByTimeFrame(Timestamp from, Timestamp to, int limit);

    void save(Reservation reservation);

    void update(Reservation reservation);

    void delete(int reservationID);
}
