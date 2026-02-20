package org.example.Mappers;

import org.example.Models.Guest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestMapper {


    public  static Guest map(ResultSet rs) throws SQLException {
        Guest guest = new Guest();
        guest.setGuestID(rs.getInt("GuestID"));
        guest.setFullName(rs.getString("FullName"));
        guest.setNIC(rs.getString("NIC"));
        guest.setContactNumber(rs.getString("ContactNumber"));
        guest.setEmail(rs.getString("Email"));
        return guest;
    }


    public static List<Guest> mapList(ResultSet rs) throws SQLException {
        List<Guest> guests = new ArrayList<>();
        while (rs.next()) {
            guests.add(map(rs));
        }
        return guests;
    }
}
