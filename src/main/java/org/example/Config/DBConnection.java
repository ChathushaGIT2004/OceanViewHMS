package org.example.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {


        private static DBConnection instance;
        private Connection connection;


        private final String URL = "jdbc:mysql://localhost:3306/OceanViewDB";
        private final String USER = "root";
        private final String PASSWORD = "0805";


        private DBConnection() throws SQLException {
            try {

                Class.forName("com.mysql.cj.jdbc.Driver");

                this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully!");
            } catch (ClassNotFoundException e) {
                System.out.println("Database Driver not found.");
                e.printStackTrace();
            }
        }


        public static DBConnection getInstance() throws SQLException {
            if (instance == null) {
                instance = new DBConnection();
            } else if (instance.getConnection().isClosed()) {
                instance = new DBConnection();
            }
            return instance;
        }


        public Connection getConnection() {
            return connection;
        }


}
