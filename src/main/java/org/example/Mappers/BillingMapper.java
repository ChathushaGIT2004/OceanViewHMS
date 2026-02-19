package org.example.Mappers;



import org.example.Models.Billing;
import org.example.Models.Reservation;
import org.example.dao.ReservationDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillingMapper {

    /**
     * Map a single ResultSet row to a Billing object
     */
    public static Billing map(ResultSet rs) throws SQLException {
        Billing billing = new Billing();
        billing.setBillID(rs.getInt("BillID"));

        // Fetch Reservation object using ReservationID
        int reservationID = rs.getInt("ReservationID");
        Reservation reservation = ReservationDAO.getReservationById(reservationID); // You need this DAO method
        billing.setReservation(reservation);

        billing.setPaymentMethod(rs.getString("PaymentMethod"));
        billing.setTotalAmount(rs.getDouble("TotalAmount"));
        billing.setTaxes(rs.getDouble("Taxes"));
        billing.setDiscount(rs.getDouble("Discount"));
        billing.setAmountPaid(rs.getDouble("AmountPaid"));
        billing.setBalanceDue(rs.getDouble("BalanceDue"));
        billing.setPaymentStatus(rs.getString("PaymentStatus"));

        return billing;
    }

    public static List<Billing> mapList(ResultSet rs) throws SQLException {
        List<Billing> billings = new ArrayList<>();
        while (rs.next()) {
            billings.add(map(rs));
        }
        return billings;
    }
}

