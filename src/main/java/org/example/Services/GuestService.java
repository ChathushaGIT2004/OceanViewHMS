package org.example.Services;

import org.example.Models.Guest;
import org.example.dao.GuestDAO;
import org.example.dao.impl.GuestDAOImpl;
import org.example.Util.SessionManager;
import org.example.Models.Session;

import java.time.LocalDateTime;

public class GuestService {

    private GuestDAO guestDAO;

    public GuestService() {
        this.guestDAO = new GuestDAOImpl();
    }


     // Find guest by NIC

    public Guest findGuestByNIC(String token, String nic) {
        if (!isSessionActive(token)) return null;
        return guestDAO.findByNIC(nic);
    }


     // Add a new guest if not exists, otherwise return existing

    public Guest addGuest(String token, String fullName, String nic, String contactNumber, String email) {
        if (!isSessionActive(token)) return null;

        Guest existing = guestDAO.findByNIC(nic);
        if (existing != null) {
            return existing;
        }

        Guest guest = new Guest();
        guest.setFullName(fullName);
        guest.setNIC(nic);
        guest.setContactNumber(contactNumber);
        guest.setEmail(email);

        guestDAO.save(guest);
        return guest;
    }



     // Update existing guest info
    public Guest updateGuest(String token, String nic, String fullName, String contactNumber, String email) {
        if (!isSessionActive(token)) return null;

        Guest guest = guestDAO.findByNIC(nic);
        if (guest == null) {
            return null;
        }

        // Update fields
        if (fullName != null && !fullName.isEmpty()) guest.setFullName(fullName);
        if (contactNumber != null && !contactNumber.isEmpty()) guest.setContactNumber(contactNumber);
        if (email != null && !email.isEmpty()) guest.setEmail(email);

        guestDAO.update(guest);
        return guest;
    }

    public boolean deleteGuest(String token, String nic) {
        if (!isSessionActive(token)) return false;

        Guest guest = guestDAO.findByNIC(nic);
        if (guest == null) return false;

        guestDAO.delete(guest.getGuestID());
        return true;
    }

    private boolean isSessionActive(String token) {
        Session session = SessionManager.getSession(token);
        if (session == null) {
            System.out.println("Session inactive or expired. Please login again.");
            return false;
        }
        return true;
    }
}