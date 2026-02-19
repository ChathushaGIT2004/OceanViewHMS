package org.example.Mappers;






import org.example.Models.User;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    public static User map(ResultSet rs) throws SQLException {

        return new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("role")
        );
    }
}
