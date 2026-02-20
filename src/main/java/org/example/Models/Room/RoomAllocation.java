package org.example.Models.Room;

import java.time.LocalDate;

public class RoomAllocation {
    private int AllocationID;
    private int reservationId;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String allocationStatus; // Reserved, Checked-in, Checked-out

    public String getAllocationStatus() {
        return allocationStatus;
    }

    public void setAllocationStatus(String allocationStatus) {
        this.allocationStatus = allocationStatus;
    }

    public int getAllocationID() {
        return AllocationID;
    }

    public void setAllocationID(int allocationID) {
        AllocationID = allocationID;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }


}
