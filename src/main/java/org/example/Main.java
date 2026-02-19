package org.example;

import org.example.Config.DBConnection;

import java.sql.Connection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Database Connected Successfully!");
            } else {
                System.out.println("❌ Connection Failed!");
            }

        } catch (Exception e) {
            System.out.println("❌ Error connecting to database");
            e.printStackTrace();
        }
    }
}