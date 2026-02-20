package org.example.dao;

import org.example.Models.Guest;


import java.util.List;
import org.example.Models.Guest;

public interface GuestDAO {
    Guest findById(int guestID);
    List<Guest> findAll();
    void save(Guest guest);
    void update(Guest guest);
    void delete(int guestID);

    Guest findByNIC(String nic);
}
