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

    CREATE_ROOM_TYPE,

    // Reservation Management (Optional)
    CREATE_RESERVATION,
    UPDATE_RESERVATION,
    DELETE_RESERVATION,
    VIEW_RESERVATIONS,

    // Activity Logs
    VIEW_ACTIVITY_LOGS ,    // For admin/manager to view user activities


    ACCESS_USERSPAGE,
    ACCESS_ROOMPAGE,
    ACCESS_RESERVATIONPAGE,
    ACCESS_GUEST_PAGE
}