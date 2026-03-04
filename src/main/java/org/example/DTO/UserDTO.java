package org.example.DTO;

public class UserDTO {

    private int userID;
    private String username;
    private String fullName;
    private String role;
    private String email;
    private String status;

    public UserDTO() {}

    public UserDTO(int userID, String username, String fullName,
                   String role, String email, String status) {
        this.userID = userID;
        this.username = username;
        this.fullName = fullName;
        this.role = role;
        this.email = email;
        this.status = status;
    }

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}