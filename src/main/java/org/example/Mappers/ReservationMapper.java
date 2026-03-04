package org.example.Mappers;

import org.example.Models.Guest;
import org.example.Models.Billings.BillableItems.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationMapper {

    // Map a single reservation WITH full guest info
    public static Reservation map(ResultSet rs) throws SQLException {

        Reservation res = new Reservation();
        res.setReservationID(rs.getInt("ReservationID"));

        // ---------- Guest Mapping ----------
        Guest guest = new Guest();
        guest.setGuestID(rs.getInt("GuestID"));
        guest.setFullName(rs.getString("FullName"));
        guest.setNIC(rs.getString("NIC"));
        guest.setContactNumber(rs.getString("ContactNumber"));
        guest.setEmail(rs.getString("Email"));




        res.setGuest(guest);

        // ---------- Reservation Details ----------
        if (rs.getTimestamp("ReservationDate") != null) {
            res.setReservationDate(
                    rs.getDate("ReservationDate")
            );
        }


        if (rs.getDate("CheckInDate") != null) {
            res.setCheckInDate(rs.getDate("CheckInDate"));
        }

        if (rs.getDate("CheckOutDate") != null) {
            res.setCheckOutDate(rs.getDate ("CheckOutDate"));
        }


        res.setTotalAmount(rs.getDouble("TotalAmount"));
        res.setStatus(rs.getString("Status"));
        res.setNumberOfGuests(rs.getInt("NumberOfGuests"));


        res.setRoomAllocationList(new ArrayList<>());

        return res;
    }

    public static List<Reservation> mapList(ResultSet rs) throws SQLException {

        List<Reservation> list = new ArrayList<>();

        while (rs.next()) {
            list.add(map(rs));
        }

        return list;
    }
}
