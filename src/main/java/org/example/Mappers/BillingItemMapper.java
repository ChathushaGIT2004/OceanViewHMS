package org.example.Mappers;

import org.example.Models.Billings.BillingItem;
import org.example.Models.Billings.BillableItem;
import org.example.Resolvers.BillableResolver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillingItemMapper {

    // Map a single BillingItem from ResultSet
    public static BillingItem map(ResultSet rs) throws SQLException {

        BillingItem item = new BillingItem();

        item.setItemID(rs.getInt("ItemID"));

        item.setQuantity(rs.getInt("Quantity"));
        item.setPrice(rs.getDouble("Price"));


        BillableItem realItem = (BillableItem) BillableResolver.resolve(item.getItemType(), rs.getInt("ItemID"));
        item.setItem(realItem);

        return item;
    }

    // Map a list of BillingItems
    public static List<BillingItem> mapList(ResultSet rs) throws SQLException {
        List<BillingItem> list = new ArrayList<>();
        while (rs.next()) {
            list.add(map(rs));
        }
        return list;
    }
}