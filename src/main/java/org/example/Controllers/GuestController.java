package org.example.Controllers;

import org.example.DTO.GuestDTO;
import org.example.Models.Guest;
import org.example.Services.GuestService;
import org.example.Util.SessionManager;

public class GuestController {

    private GuestService guestService;

    public GuestController() {
        this.guestService = new GuestService();
    }


     // Check if guest exists by NIC or add new guest

    public Guest handleGuest(String token, GuestDTO guestDTO) {
        if (!isSessionActive(token)) return null;

        // First, check if guest exists
        Guest existingGuest = guestService.findGuestByNIC(token, guestDTO.getNic());
        if (existingGuest != null) {
            System.out.println("Guest already registered: " + existingGuest.getFullName());
            return existingGuest;
        }

        // Add new guest if not found
        Guest newGuest = guestService.addGuest(
                token,
                guestDTO.getFullName(),
                guestDTO.getNic(),
                guestDTO.getContactNumber(),
                guestDTO.getEmail()
        );

        System.out.println("New guest added: " + newGuest.getFullName());
        return newGuest;
    }


     // Update guest info

    public Guest updateGuest(String token, String nic, GuestDTO updatedInfo) {
        if (!isSessionActive(token)) return null;

        Guest updatedGuest = guestService.updateGuest(
                token,
                nic,
                updatedInfo.getFullName(),
                updatedInfo.getContactNumber(),
                updatedInfo.getEmail()
        );

        if (updatedGuest != null) {
            System.out.println("Guest updated: " + updatedGuest.getFullName());
        } else {
            System.out.println("Guest not found for NIC: " + nic);
        }
        return updatedGuest;
    }


     // Delete guest

    public boolean deleteGuest(String token, String nic) {
        if (!isSessionActive(token)) return false;

        boolean deleted = guestService.deleteGuest(token, nic);

        if (deleted) {
            System.out.println("Guest deleted: " + nic);
        } else {
            System.out.println("Guest not found or could not delete: " + nic);
        }
        return deleted;
    }

    /**
     * Validate session token
     */
    private boolean isSessionActive(String token) {
        if (SessionManager.getSession(token) == null) {
            System.out.println("Session inactive or expired. Please login again.");
            return false;
        }
        return true;
    }
}