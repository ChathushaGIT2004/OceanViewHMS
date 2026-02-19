package org.example.Test;

import org.example.Config.DBConnection;
import java.sql.Connection;

public class TestDBConnection {

      public   TestDBConnection() {

            try {
                Connection conn = DBConnection.getInstance().getConnection();

                if (conn != null && !conn.isClosed()) {
                    System.out.println(" Database Connected Successfully.");
                } else {
                    System.out.println(" Connection Failed.");
                }

            } catch (Exception e) {
                System.out.println(" Error connecting to database");
                e.printStackTrace();
            }
        }


}
