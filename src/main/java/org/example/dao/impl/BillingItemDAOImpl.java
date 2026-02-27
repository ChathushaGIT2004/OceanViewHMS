package org.example.dao.impl;

import org.example.Models.Billings.BillingItem;
import org.example.Mappers.BillingItemMapper;
import org.example.Util.DBConnection;
import org.example.dao.BillingItemDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillingItemDAOImpl implements BillingItemDAO {

    private static Connection conn;


    public BillingItemDAOImpl() {
        try {
            conn = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public BillingItem getById(int itemID) {
        String sql = "SELECT * FROM BillingItem WHERE ItemID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itemID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return BillingItemMapper.map(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 🔍 Get all items for a Bill
    @Override
    public List<BillingItem> getByBillId(int billID) {
        String sql = "SELECT * FROM BillingItem WHERE BillID = ?";

        List<BillingItem> list = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, billID);

            ResultSet rs = ps.executeQuery();

            list = BillingItemMapper.mapList(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    @Override
    public List<BillingItem> getAll() {

        String sql = "SELECT * FROM BillingItem";

        List<BillingItem> list = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            // ✅ Use your mapper to convert ResultSet → Objects
            list = BillingItemMapper.mapList(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    @Override
    public void save(int billID, BillingItem item) {

        String sql = """
            INSERT INTO BillingItem
            (BillID, ItemType, ItemRefID, Quantity, Price)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, billID);
            ps.setString(2, item.getItemType());
            ps.setInt(3, item.getItemID());
            ps.setInt(4, item.getQuantity());
            ps.setDouble(5, item.getPrice());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✏️ Update BillingItem
    @Override
    public void update(BillingItem item) {

        String sql = """
            UPDATE BillingItem
            SET ItemType = ?, ItemRefID = ?, Quantity = ?, Price = ?
            WHERE ItemID = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getItemType());
            ps.setInt(2, item.getItemID());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getPrice());
            ps.setInt(5, item.getItemID());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int itemID) {

        String sql = "DELETE FROM BillingItem WHERE ItemID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, itemID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
