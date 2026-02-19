package org.example.dao;

import org.example.Models.Reservation;

public interface ReservationDAO {

    void addReservation(Reservation reservation);

    Reservation getReservation(int reservationId);
}
