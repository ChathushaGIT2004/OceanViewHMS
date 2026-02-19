package org.example.dao.impl;

import org.example.Config.DBConnection;
import org.example.Models.Billing;
import org.example.dao.BillingDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class BillingDAOImpl implements BillingDAO {

    @Override
    public void saveBill(Billing bill) {

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "INSERT INTO bills(reservation_id, total) VALUES(?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setInt(1, bill.getBillId());
            pst.setDouble(2, bill.getAmount());

            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


