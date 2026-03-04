package org.example.DTO;


public class GuestInfoDTO {
    private int guestID;
    private String nic;
    private String fullName;

    public int getGuestID() { return guestID; }
    public void setGuestID(int guestID) { this.guestID = guestID; }

    public String getNic() { return nic; }
    public void setNic(String nic) { this.nic = nic; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
}