package org.example.Models;

class Room {
    private int roomID;
    private RoomType roomType;
    private String roomStatus; // Available, Occupied, Maintenance

    public int getRoomID() {
        return roomID;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }
}

