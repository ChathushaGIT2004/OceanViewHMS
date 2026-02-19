package org.example.Mappers;



import org.example.Models.Billing;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BillingMapper {

    public static Billing map(ResultSet rs) throws SQLException {

        return new Billing(
                rs.getInt("booking_id"),
                rs.getInt("guest_id"),
                rs.getInt("room_id"),
                rs.getDate("check_in"),
                rs.getDate("check_out"),
                rs.getString("status")
        );
    }
}
