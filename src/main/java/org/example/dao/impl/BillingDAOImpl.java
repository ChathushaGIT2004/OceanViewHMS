package org.example.dao.impl;

import org.example.Models.Billings.Billing;
import org.example.dao.BillingDAO;

import java.util.List;
import java.util.ArrayList;


public class BillingDAOImpl implements BillingDAO {

    @Override
    public Billing findById(int billID) {
        return null;
    }

    @Override
    public List<Billing> findByReservationId(int reservationID) {
        return new ArrayList<>();
    }

    @Override
    public List<Billing> findAll() {
        return new ArrayList<>();
    }

    @Override
    public void save(Billing billing) {
    }

    @Override
    public void update(Billing billing) {
    }

    @Override
    public void delete(int billID) {
    }
}


