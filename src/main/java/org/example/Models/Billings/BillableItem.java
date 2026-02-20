package org.example.Models.Billings;


public interface BillableItem {

    int getId();
    String getItemType();
    double getPrice();

}