package org.example.DTO;

import java.util.Date;
import java.util.List;

public class AddReservationRequestDTO {
    private String token;
    private int guestID;
    private Date checkIn;
    private Date checkOut;
    private int numberOfGuests;
    private String status;
    private List<Integer> roomIds;

    // Getters & Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public int getGuestID() { return guestID; }
    public void setGuestID(int guestID) { this.guestID = guestID; }

    public Date getCheckIn() { return checkIn; }
    public void setCheckIn(Date checkIn) { this.checkIn = checkIn; }

    public Date getCheckOut() { return checkOut; }
    public void setCheckOut(Date checkOut) { this.checkOut = checkOut; }

    public int getNumberOfGuests() { return numberOfGuests; }
    public void setNumberOfGuests(int numberOfGuests) { this.numberOfGuests = numberOfGuests; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<Integer> getRoomIds() { return roomIds; }
    public void setRoomIds(List<Integer> roomIds) { this.roomIds = roomIds; }
}