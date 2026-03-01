package org.example.Models;

import org.example.Models.Billings.BillableItem;
import org.example.Models.Room.RoomAllocation;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Reservation implements BillableItem {
    private int reservationID;
    private Guest guest;
    private LocalDateTime reservationDate;
    private Timestamp checkInDate;
    private Timestamp checkOutDate;
    private int numberOfGuests;
    private double totalAmount;
    private String status; // Pending, Confirmed, Cancelled

     private List<RoomAllocation> roomAllocationList;

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RoomAllocation> getRoomAllocationList() {
        return roomAllocationList;
    }

    public void setRoomAllocationList(List<RoomAllocation> roomAllocationList) {
        this.roomAllocationList = roomAllocationList;
    }

    @Override
    public int getId() {
        return reservationID;
    }

    @Override
    public String getItemType() {
        return "reservation";
    }

    @Override
    public double getPrice() {
        return totalAmount;
    }


    public Timestamp getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Timestamp checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Timestamp getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Timestamp checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
}