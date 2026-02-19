package org.example.Mappers;

import org.example.Models.Guest;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuestMapper {

    public static Guest map(ResultSet rs) throws SQLException {

        return new Guest(
                rs.getInt("guest_id"),
                rs.getString("name"),
                rs.getString("phone"),
                rs.getString("email")
        );
    }
}
