package org.example.dao;

import org.example.DTO.ResponseMessageDTO;
import org.example.Models.Billings.BillableItems.Reservation;

import java.sql.Timestamp;
import java.util.List;

public interface ReservationDAO {


    Reservation getById(int reservationID);

    List<Reservation> getByGuestId(int guestID);

    List<Reservation> getAll();

    List<Reservation> getLatest(int limit);

    List<Reservation> getByTimeFrame(Timestamp from, Timestamp to, int limit);

    ResponseMessageDTO save(Reservation reservation);

    ResponseMessageDTO update(Reservation reservation);

    void delete(int reservationID);
}
