package org.example.Mappers;




import org.example.Models.Room;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomMapper {

    public static Room map(ResultSet rs) throws SQLException {

        return new Room(
                rs.getInt("room_id"),
                rs.getString("type"),
                rs.getDouble("price"),
                rs.getBoolean("available")
        );
    }
}
