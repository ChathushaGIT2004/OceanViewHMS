package org.example.dao.impl;

import org.example.dao.UserDAO;
import org.example.Models.User;
import org.example.Config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAOImpl implements UserDAO {

    @Override
    public User findByUsername(String username) {

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}


