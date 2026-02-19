package org.example.Models;

public class Room {

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    private int roomId;
    private String roomType;
    private double pricePerNight;
    private boolean available;

    public Room(int roomId, String type, double price) {
    }
}
