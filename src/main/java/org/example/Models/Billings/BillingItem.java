package org.example.Models.Billings;

public class BillingItem {

    private int itemID;


    private BillableItem item;
    private  String itemType;
    private int quantity;
    private double price;

    public BillingItem() {}

    public BillableItem getItem() {
        return item;
    }

    public void setItem(BillableItem item) {
        this.item = item;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItemType() {
        return this.itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}