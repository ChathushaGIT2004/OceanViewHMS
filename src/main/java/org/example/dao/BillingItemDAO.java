package org.example.dao;


import org.example.Models.Billings.BillingItem;
import java.util.List;

public interface BillingItemDAO {

    BillingItem getById(int itemID);

    List<BillingItem> getByBillId(int billID);

    List<BillingItem> getAll();

    void save(int billID, BillingItem item);

    void update(BillingItem item);

    void delete(int itemID);
}