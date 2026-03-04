package org.example.DTO;

public class UpdateStatusDTO {
    private String token;
    private int iD;
    private String status;



    // Getters & setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public int getID() { return iD; }
    public void setReservationID(int iD) { this.iD = iD; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}