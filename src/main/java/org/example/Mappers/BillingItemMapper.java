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

        int billingItemID = rs.getInt("ItemID");
        int refID = rs.getInt("ItemRefID");
        String itemType = rs.getString("ItemType");


        item.setItemID(refID); // this is the REAL object ID
        item.setItemType(itemType);
        item.setQuantity(rs.getInt("Quantity"));
        item.setPrice(rs.getDouble("Price"));

        // Now resolve correctly
        BillableItem realItem =
               (BillableItem) BillableResolver.resolve(itemType, refID);

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