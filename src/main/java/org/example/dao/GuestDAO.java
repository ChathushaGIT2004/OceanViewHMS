package org.example.dao;

import org.example.Models.Guest;

public interface GuestDAO {

    void addGuest(Guest guest);

    Guest getGuestById(int guestId);

}
