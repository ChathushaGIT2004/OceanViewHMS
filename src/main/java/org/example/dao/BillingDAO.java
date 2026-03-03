package org.example.dao;

import org.example.DTO.ResponseMessageDTO;
import org.example.Models.Billings.Billing;
import java.util.List;


public interface BillingDAO {
    Billing findById(int billID);


    List<Billing> findByGuestId(int guestID);

    List<Billing> findAll();
    ResponseMessageDTO save(Billing billing);
    void update(Billing billing);

    // 💳 Pay Billing
    ResponseMessageDTO pay(int billID, double amount, String paymentMethod);

    void delete(int billID);
}
