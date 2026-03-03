package org.example.Mappers;

import org.example.Models.Billings.Billing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillingMapper {

    public static Billing map(ResultSet rs) throws SQLException {
        Billing billing = new Billing();

        billing.setBillID(rs.getInt("BillID"));
        billing.setGuestID(rs.getInt("GuestID"));

        billing.setNetPrice(rs.getDouble("NetPrice"));
        billing.setGrossPrice(rs.getDouble("GrossPrice"));

        billing.setTaxes(rs.getDouble("Taxes"));
        billing.setDiscount(rs.getDouble("Discount"));
        billing.setAmountPaid(rs.getDouble("AmountPaid"));
        billing.setBalanceDue(rs.getDouble("BalanceDue"));
        billing.setPaymentStatus(rs.getString("PaymentStatus"));
        billing.setPaymentMethod(rs.getString("PaymentMethod"));

        // Map new timestamp fields
        billing.setCreatedAt(rs.getTimestamp("CreatedAt"));
        billing.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
        billing.setPaymentDate(rs.getTimestamp("PaymentDate"));


        return billing;
    }

    public static List<Billing> mapList(ResultSet rs) throws SQLException {
        List<Billing> list = new ArrayList<>();
        while (rs.next()) {
            list.add(map(rs));
        }
        return list;
    }
}