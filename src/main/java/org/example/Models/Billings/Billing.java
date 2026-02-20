package org.example.Models.Billings;

import java.util.List;

public class Billing {

    private int billID;
    private double totalAmount;
    private double taxes;
    private double discount;
    private double amountPaid;
    private double balanceDue;
    private String paymentMethod;
    private String paymentStatus;

    // 🔥 Correct: a bill has MANY items
    private List<BillingItem> items;

    public int getBillID() { return billID; }
    public void setBillID(int billID) { this.billID = billID; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public double getTaxes() { return taxes; }
    public void setTaxes(double taxes) { this.taxes = taxes; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }

    public double getBalanceDue() { return balanceDue; }
    public void setBalanceDue(double balanceDue) { this.balanceDue = balanceDue; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public List<BillingItem> getItems() { return items; }
    public void setItems(List<BillingItem> items) { this.items = items; }
}