package org.example.Services;

import org.example.DTO.GuestDTO;
import org.example.Models.Guest;
import org.example.Models.User.UserActivityLog;
import org.example.Util.EmailUtil;
import org.example.dao.GuestDAO;
import org.example.dao.UserActivityLogDAO;
import org.example.dao.impl.GuestDAOImpl;
import org.example.Util.SessionManager;
import org.example.Models.Session;
import org.example.dao.impl.UserActivityLogDAOImpl;

import java.time.LocalDateTime;
import java.util.List;

public class GuestService {

    private final GuestDAO guestDAO;
    private  UserActivityLogService userActivityLogService;
    private EmailUtil emailUtil=new EmailUtil();

    public GuestService() {
        this.guestDAO = new GuestDAOImpl();
    }


     // Find guest by NIC

    public Guest findGuestByNIC( String nic) {
        return guestDAO.findByNIC(nic);
    }


    // Find guest by NIC

    public Guest findGuestByID( int id) {
        return guestDAO.findById(id);
    }

    // Add a new guest if not exists, otherwise return existing

    public Guest addGuest(String token, GuestDTO guestDTO) throws Exception {
       // if (!isSessionActive(token)) return null;
          System.out.println(guestDTO.getFullName());
        Guest existing = guestDAO.findByNIC(guestDTO.getNic());
        if (existing != null) {
            return existing;
        }

        Guest guest = new Guest();
        guest.setFullName(guestDTO.getFullName());
        guest.setNIC(guestDTO.getNic());
        guest.setContactNumber(guestDTO.getContactNumber());
        guest.setEmail(guestDTO.getEmail());

        guestDAO.save(guest);
        String subject = "Welcome to Ocean View Hotel .";
        String body = String.format(
                "Dear %s,\n\n" +
                        "You have been successfully added as a guest to our Oceam View Hotel Management Database.\n\n" +
                        "Through this, you will receive our latest offers, updates, and exclusive promotions directly from us.\n\n" +
                        "We are excited to have you with us!\n\n" +
                        "Best regards,\n" +
                        "OceanView Hotel Management",
                guest.getFullName()
        );

        emailUtil.sendPlainTextEmail(guest.getEmail(), subject, body);

        System.out.println("Guest Saved");
         //adding Log
        UserActivityLogService.getInstance().log(
                token,
                "CREATE",
                "BILLING",
                 guest.getGuestID(),
                "Added a new  guest"
        );



        return guest;
    }

    public List<Guest> getAllGuests() {
        try {
            return guestDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
        boolean session = SessionManager.isValidToken(token);
        if (!session) {
            System.out.println("Session inactive or expired. Please login again.");
            return false;
        }
        return true;
    }
}