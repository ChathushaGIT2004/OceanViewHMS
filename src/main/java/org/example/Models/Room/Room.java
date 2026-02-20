package org.example.Models.Room;

public class Room {
    private int roomID;
    private int roomType;
    private String roomStatus; // Available, Occupied, Maintenance

    public int getRoomID() {
        return roomID;
    }

    public int getRoomType() {
        return roomType;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }
}

