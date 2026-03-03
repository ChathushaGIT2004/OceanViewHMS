package org.example.dao.impl;

import org.example.DTO.ResponseMessageDTO;
import org.example.Models.Billings.Billing;
import org.example.Util.DBConnection;
import org.example.dao.BillingDAO;
import org.example.Mappers.BillingMapper;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class BillingDAOImpl implements BillingDAO {

    private final Connection conn;

    public BillingDAOImpl() {
        try {
            this.conn = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 🔎 Find Billing by Bill ID
    @Override
    public Billing findById(int billID) {
        String sql = "SELECT * FROM Billing WHERE BillID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, billID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return BillingMapper.map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔎 Find Billing by Guest ID
    @Override
    public List<Billing> findByGuestId(int guestID) {
        List<Billing> list = new ArrayList<>();
        String sql = "SELECT * FROM Billing WHERE GuestID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, guestID);
            ResultSet rs = ps.executeQuery();
            list = BillingMapper.mapList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 🔎 Get All Billing Records
    @Override
    public List<Billing> findAll() {
        List<Billing> list = new ArrayList<>();
        String sql = "SELECT * FROM Billing ORDER BY CreatedAt DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            list = BillingMapper.mapList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 💾 Save New Billing
    @Override
    public ResponseMessageDTO save(Billing billing) {
        String sql = "INSERT INTO Billing " +
                "(GuestID, NetPrice, GrossPrice, Taxes, Discount, AmountPaid, BalanceDue, PaymentStatus, PaymentMethod, CreatedAt, UpdatedAt, PaymentDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, billing.getGuestID());
            ps.setDouble(2, billing.getNetPrice());
            ps.setDouble(3, billing.getGrossPrice());
            ps.setDouble(4, billing.getTaxes());
            ps.setDouble(5, billing.getDiscount());
            ps.setDouble(6, billing.getAmountPaid());
            ps.setDouble(7, billing.getBalanceDue());
            ps.setString(8, billing.getPaymentStatus());
            ps.setString(9, billing.getPaymentMethod());

            // Set timestamps
            Timestamp now = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(10, now); // CreatedAt
            ps.setTimestamp(11, now); // UpdatedAt

            if (billing.getPaymentDate() != null)
                ps.setTimestamp(12, new Timestamp(billing.getPaymentDate().getTime()));
            else
                ps.setNull(12, Types.TIMESTAMP);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int generatedBillID = rs.getInt(1);
                    ResponseMessageDTO response = new ResponseMessageDTO();
                    response.setSuccess(true);
                    response.setMessage("Billing created successfully");
                    response.setData(generatedBillID);
                    return response;
                }
            }
            return new ResponseMessageDTO(false, "Failed to create billing");
        } catch (SQLException e) {
            return new ResponseMessageDTO(false, "Database Error: " + e.getMessage());
        }
    }

    // ✏ Update Billing
    @Override
    public void update(Billing billing) {
        String sql = "UPDATE Billing SET " +
                "GuestID=?, NetPrice=?, GrossPrice=?, Taxes=?, Discount=?, AmountPaid=?, BalanceDue=?, PaymentStatus=?, PaymentMethod=?, UpdatedAt=?, PaymentDate=? " +
                "WHERE BillID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, billing.getGuestID());
            ps.setDouble(2, billing.getNetPrice());
            ps.setDouble(3, billing.getGrossPrice());
            ps.setDouble(4, billing.getTaxes());
            ps.setDouble(5, billing.getDiscount());
            ps.setDouble(6, billing.getAmountPaid());
            ps.setDouble(7, billing.getBalanceDue());
            ps.setString(8, billing.getPaymentStatus());
            ps.setString(9, billing.getPaymentMethod());
            ps.setTimestamp(10, new Timestamp(System.currentTimeMillis())); // UpdatedAt

            if (billing.getPaymentDate() != null)
                ps.setTimestamp(11, new Timestamp(billing.getPaymentDate().getTime()));
            else
                ps.setNull(11, Types.TIMESTAMP);

            ps.setInt(12, billing.getBillID());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 💳 Pay Billing
    @Override
    public ResponseMessageDTO pay(int billID, double amount, String paymentMethod) {
        Billing billing = findById(billID);
        if (billing == null) return new ResponseMessageDTO(false, "Billing not found");

        double newAmountPaid = billing.getAmountPaid() + amount;
        double newBalance = billing.getGrossPrice() - newAmountPaid;
        String status = newBalance <= 0 ? "Paid" : "Partially Paid";

        String sql = "UPDATE Billing SET AmountPaid=?, BalanceDue=?, PaymentStatus=?, PaymentMethod=?, PaymentDate=?, UpdatedAt=? WHERE BillID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newAmountPaid);
            ps.setDouble(2, newBalance);
            ps.setString(3, status);
            ps.setString(4, paymentMethod);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(5, now); // PaymentDate
            ps.setTimestamp(6, now); // UpdatedAt
            ps.setInt(7, billID);

            int rows = ps.executeUpdate();
            return rows > 0
                    ? new ResponseMessageDTO(true, "Payment recorded successfully")
                    : new ResponseMessageDTO(false, "Payment failed");
        } catch (SQLException e) {
            return new ResponseMessageDTO(false, "Database error: " + e.getMessage());
        }
    }

    // ❌ Delete Billing
    @Override
    public void delete(int billID) {
        String sql = "DELETE FROM Billing WHERE BillID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, billID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}