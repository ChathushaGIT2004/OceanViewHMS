package org.example.Mappers;

import org.example.Models.Billings.Billing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillingMapper {

    // Map single record
    public static Billing map(ResultSet rs) throws SQLException {
        Billing billing = new Billing();

        billing.setBillID(rs.getInt("BillID"));

        billing.setPaymentMethod(rs.getString("PaymentMethod"));
        billing.setTotalAmount(rs.getDouble("TotalAmount"));
        billing.setTaxes(rs.getDouble("Taxes"));
        billing.setDiscount(rs.getDouble("Discount"));
        billing.setAmountPaid(rs.getDouble("AmountPaid"));
        billing.setBalanceDue(rs.getDouble("BalanceDue"));
        billing.setPaymentStatus(rs.getString("PaymentStatus"));

        return billing;
    }

    // Map list of records
    public static List<Billing> mapList(ResultSet rs) throws SQLException {
        List<Billing> list = new ArrayList<>();
        while (rs.next()) {
            list.add(map(rs));
        }
        return list;
    }
}
