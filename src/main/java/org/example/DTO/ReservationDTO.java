package org.example.DTO;

import java.util.Date;
import java.util.List;

public class ReservationDTO {
    private  int ReservationID;
    private int guestID;
    private  String guestFullname;
    private Date checkIn;
    private Date checkOut;
    private int numberOFGuests;
    private double totalAmount;
    private String status;
    private List<Integer> roomIds; // List of room IDs to allocate

    // Getters and setters
    public int getGuestID() { return guestID; }
    public void setGuestID(int guestID) { this.guestID = guestID; }

    public Date getCheckIn() { return checkIn; }
    public void setCheckIn(Date checkIn) { this.checkIn = checkIn; }

    public Date getCheckOut() { return checkOut; }
    public void setCheckOut(Date checkOut) { this.checkOut = checkOut; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<Integer> getRoomIds() { return roomIds; }
    public void setRoomIds(List<Integer> roomIds) { this.roomIds = roomIds; }

    public int getReservationID() {
        return ReservationID;
    }

    public void setReservationID(int reservationID) {
        ReservationID = reservationID;
    }

    public String getGuestFullname() {
        return guestFullname;
    }

    public void setGuestFullname(String guestFullname) {
        this.guestFullname = guestFullname;
    }

    public int getNumberOFGuests() {
        return numberOFGuests;
    }

    public void setNumberOFGuests(int numberOFGuests) {
        this.numberOFGuests = numberOFGuests;
    }
}