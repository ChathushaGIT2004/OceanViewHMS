package org.example.Mappers;






import org.example.Models.User;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static User map(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserID(rs.getInt("UserID"));
        user.setUsername(rs.getString("Username"));
        user.setPasswordHash(rs.getString("PasswordHash"));
        user.setFullName(rs.getString("FullName"));
        user.setRole(rs.getString("Role"));
        user.setEmail(rs.getString("Email"));
        user.setStatus(rs.getString("Status"));

        return user;
    }

    public static List<User> mapList(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            users.add(map(rs));
        }
        return users;
    }
}

