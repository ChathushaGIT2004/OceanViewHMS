package org.example.DTO;

public class GuestDTO {
    private String fullName;
    private String nic;
    private String contactNumber;
    private String email;

    public GuestDTO() {}

    public GuestDTO(String fullName, String nic, String contactNumber, String email) {
        this.fullName = fullName;
        this.nic = nic;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}