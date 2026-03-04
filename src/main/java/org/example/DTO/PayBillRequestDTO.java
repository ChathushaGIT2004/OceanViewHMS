package org.example.DTO;

public class PayBillRequestDTO {
    private String token;
    private int billID;
    private double amount;
    private String paymentMethod;

    public PayBillRequestDTO() {} // default constructor

    // Getters & Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public int getBillID() { return billID; }
    public void setBillID(int billID) { this.billID = billID; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}