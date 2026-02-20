package org.example.Models.User;

import java.time.LocalDateTime;

public class UserActivityLog {
    private int activityID;
    private int userID;
    private String actionType;        // Create, Update, Delete, Login, Logout
    private String actionTarget;      // Guest, Reservation, Booking, Room, Billing
    private int targetReferenceID;
    private String actionDetails;
    private LocalDateTime actionTimestamp;

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionTarget() {
        return actionTarget;
    }

    public void setActionTarget(String actionTarget) {
        this.actionTarget = actionTarget;
    }

    public int getTargetReferenceID() {
        return targetReferenceID;
    }

    public void setTargetReferenceID(int targetReferenceID) {
        this.targetReferenceID = targetReferenceID;
    }

    public String getActionDetails() {
        return actionDetails;
    }

    public void setActionDetails(String actionDetails) {
        this.actionDetails = actionDetails;
    }

    public LocalDateTime getActionTimestamp() {
        return actionTimestamp;
    }

    public void setActionTimestamp(LocalDateTime actionTimestamp) {
        this.actionTimestamp = actionTimestamp;
    }
}
