package org.example.Models;


public class Billing {

    private int billId;

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public org.example.Models.Reservation getReservation() {
        return reservation;
    }

    public void setReservation(org.example.Models.Reservation reservation) {
        this.reservation = reservation;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    private org.example.Models.Reservation reservation;
    private double amount;

}
