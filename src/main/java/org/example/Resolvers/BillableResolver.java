package org.example.Resolvers;

import org.example.dao.impl.ReservationDAOImpl;
import org.example.dao.impl.RoomAllocationDAOImpl;

public class BillableResolver {

    public static Object resolve(String itemType, int refID) {

        try {
            return switch (itemType) {
                case "Reservation" -> new ReservationDAOImpl().getById(refID);
                case "RoomAllocation" -> new RoomAllocationDAOImpl().getdById(refID);
                default -> null;
            };

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}