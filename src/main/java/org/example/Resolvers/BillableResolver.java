package org.example.Resolvers;

import java.sql.Connection;

import org.example.Config.DBConnection;
import org.example.dao.impl.ReservationDAOImpl;
import org.example.dao.impl.RoomAllocationDAOImpl;

public class BillableResolver {

    // Static method that fetches the connection internally
    public static Object resolve(String itemType, int refID) {
        try (Connection conn = DBConnection.getInstance().getConnection()) {
            return switch (itemType) {
                case "Reservation" -> new ReservationDAOImpl(conn).getById(refID);
                // case "Service" -> new ServiceDAOImpl(conn).getById(refID);
                // case "VIPPackage" -> new PackageDAOImpl(conn).getById(refID);
                case "RoomAllocation" -> new RoomAllocationDAOImpl(conn).getdById(refID);
                default -> null;
            };
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}