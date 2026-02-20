package org.example.Models.Room;

public class RoomType {
    private int roomTypeID;
    private String typeName;
    private double chargePerNight;
    private int occupancyLimit;

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getChargePerNight() {
        return chargePerNight;
    }

    public void setChargePerNight(double chargePerNight) {
        this.chargePerNight = chargePerNight;
    }

    public int getOccupancyLimit() {
        return occupancyLimit;
    }

    public void setOccupancyLimit(int occupancyLimit) {
        this.occupancyLimit = occupancyLimit;
    }
}
