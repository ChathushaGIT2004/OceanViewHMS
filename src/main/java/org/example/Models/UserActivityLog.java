package org.example.Models;

import java.time.LocalDateTime;

class UserActivityLog {
    private int activityID;
    private int userID;
    private String actionType;        // Create, Update, Delete, Login, Logout
    private String actionTarget;      // Guest, Reservation, Booking, Room, Billing
    private int targetReferenceID;
    private String actionDetails;
    private LocalDateTime actionTimestamp;


}
