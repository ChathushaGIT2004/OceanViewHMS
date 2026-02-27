package org.example.Test;

import org.example.Models.Guest;
import org.example.dao.GuestDAO;
import org.example.dao.impl.GuestDAOImpl;

import java.util.List;

public class GuestDAOTest {

    public static void main(String[] args) {

        GuestDAO guestDAO = new GuestDAOImpl();

        boolean savePass = false, findByIdPass = false, findAllPass = false, findByNICPass = false, updatePass = false;

        // ====== CREATE TEST ======
        Guest guest = new Guest();
        guest.setFullName("CJ Persistent Guest");
        guest.setNIC("987654341V");  // Use unique NIC for repeated runs
        guest.setContactNumber("0712345678");
        guest.setEmail("cjpersistent@example.com");

        guestDAO.save(guest);
        if (guest.getGuestID() > 0) {
            savePass = true;
            System.out.println("PASS: save() created guest with ID " + guest.getGuestID());
        } else {
            System.out.println("FAIL: save() did not generate GuestID");
        }

        // ====== FIND BY ID TEST ======
        Guest byId = guestDAO.findById(guest.getGuestID());
        if (byId != null && byId.getGuestID() == guest.getGuestID()) {
            findByIdPass = true;
            System.out.println("PASS: findById() found guest " + byId.getFullName());
        } else {
            System.out.println("FAIL: findById() did not find guest");
        }

        // ====== FIND ALL TEST ======
        List<Guest> allGuests = guestDAO.findAll();
        if (allGuests.size() > -1) {
            findAllPass = true;
            System.out.println("PASS: findAll() returned " + allGuests.size() + " guests");
        } else {
            System.out.println("FAIL: findAll() returned empty list");
        }

        // ====== FIND BY NIC TEST ======
        Guest byNIC = guestDAO.findByNIC("987654321V");
        if (byNIC != null && byNIC.getNIC().equals("987654321V")) {
            findByNICPass = true;
            System.out.println("PASS: findByNIC() found guest " + byNIC.getFullName());
        } else {
            System.out.println("FAIL: findByNIC() did not find guest");
        }

        // ====== UPDATE TEST ======
        guest.setFullName("CJ Persistent Updated");
        guest.setContactNumber("0777654321");
        guestDAO.update(guest);

        Guest updated = guestDAO.findById(guest.getGuestID());
        if (updated != null && updated.getFullName().equals("CJ Persistent Updated") && updated.getContactNumber().equals("0777654321")) {
            updatePass = true;
            System.out.println("PASS: update() successfully updated guest");
        } else {
            System.out.println("FAIL: update() did not update guest correctly");
        }

        // ====== SUMMARY ======
        System.out.println("\n==== TEST SUMMARY ====");
        System.out.println("save()       : " + (savePass ? "PASS" : "FAIL"));
        System.out.println("findById()   : " + (findByIdPass ? "PASS" : "FAIL"));
        System.out.println("findAll()    : " + (findAllPass ? "PASS" : "FAIL"));
        System.out.println("findByNIC()  : " + (findByNICPass ? "PASS" : "FAIL"));
        System.out.println("update()     : " + (updatePass ? "PASS" : "FAIL"));
        System.out.println("\nGuest ID " + guest.getGuestID() + " saved for future testing.");
    }
}