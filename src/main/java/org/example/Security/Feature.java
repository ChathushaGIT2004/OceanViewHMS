package org.example.Security;

public enum Feature {

    // User Management
    CREATE_USER,
    UPDATE_USER,
    DELETE_USER,
    VIEW_USERS,
    UPDATE_PASSWORD,       // For changing own password

    // Billing Management
    CREATE_BILLING,
    VIEW_BILLING,
    DELETE_BILLING,

    // Room Management (Optional if you extend later)
    CREATE_ROOM,
    UPDATE_ROOM,
    DELETE_ROOM,
    VIEW_ROOMS,

    // Reservation Management (Optional)
    CREATE_RESERVATION,
    UPDATE_RESERVATION,
    DELETE_RESERVATION,
    VIEW_RESERVATIONS,

    // Activity Logs
    VIEW_ACTIVITY_LOGS     // For admin/manager to view user activities
}