package org.example.Models.Billings;

import java.util.Date;
import java.util.List;

public class Billing {

    private int billID;
    private int guestID;
    private double netPrice;
    private double grossPrice;
    private double taxes;
    private double discount;
    private double amountPaid;
    private double balanceDue;
    private String paymentMethod;
    private String paymentStatus;

    private Date createdAt;       // New
    private Date updatedAt;       // New
    private Date paymentDate;     // New

    private List<BillingItem> items;

    // ===== Getters & Setters =====
    public int getBillID() { return billID; }
    public void setBillID(int billID) { this.billID = billID; }

    public int getGuestID() { return guestID; }
    public void setGuestID(int guestID) { this.guestID = guestID; }

    public double getNetPrice() { return netPrice; }
    public void setNetPrice(double netPrice) { this.netPrice = netPrice; }

    public double getGrossPrice() { return grossPrice; }
    public void setGrossPrice(double grossPrice) { this.grossPrice = grossPrice; }

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

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }

    public List<BillingItem> getItems() { return items; }
    public void setItems(List<BillingItem> items) { this.items = items; }
}