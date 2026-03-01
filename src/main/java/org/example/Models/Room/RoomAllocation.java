package org.example.Models.Room;

import java.time.LocalDate;

public class RoomAllocation {
    private int AllocationID;
    private int reservationId;
    private Room room;

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




}
