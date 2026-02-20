package org.example.Resolvers;

import java.sql.Connection;

import org.example.dao.impl.ReservationDAOImpl;
import org.example.dao.impl.RoomAllocationDAOImpl;


public class BillableResolver {

    private final Connection conn;

    public BillableResolver(Connection conn) {
        this.conn = conn;
    }

    public static Object resolve(String itemType, int refID) {

        return switch (itemType) {

            case "Reservation" -> new ReservationDAOImpl(conn).getById(refID);

         //   case "Service" -> new ServiceDAOImpl(conn).getById(refID);

         //   case "VIPPackage" -> new PackageDAOImpl(conn).getById(refID);

            case "RoomAllocation" -> new RoomAllocationDAOImpl(conn).getdById(refID);

            default -> null;
        };
    }
}