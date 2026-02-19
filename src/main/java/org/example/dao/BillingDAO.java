package org.example.dao;

import org.example.Models.Billing;
import java.util.List;


public interface BillingDAO {
    Billing findById(int billID);
    List<Billing> findByReservationId(int reservationID);
    List<Billing> findAll();
    void save(Billing billing);
    void update(Billing billing);
    void delete(int billID);
}
