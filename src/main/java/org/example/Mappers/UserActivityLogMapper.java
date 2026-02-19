package org.example.Mappers;


import org.example.Models.UserActivityLog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class UserActivityLogMapper {

    /**
     * Map a single ResultSet row to a UserActivityLog object
     */
    public static UserActivityLog map(ResultSet rs) throws SQLException {
        UserActivityLog log = new UserActivityLog();
        log.setActivityID(rs.getInt("ActivityID"));
        log.setUserID(rs.getInt("UserID"));
        log.setActionType(rs.getString("ActionType"));
        log.setActionTarget(rs.getString("ActionTarget"));
        log.setTargetReferenceID(rs.getInt("TargetReferenceID"));
        log.setActionDetails(rs.getString("ActionDetails"));

        // Convert SQL Timestamp to LocalDateTime
        java.sql.Timestamp timestamp = rs.getTimestamp("ActionTimestamp");
        if (timestamp != null) {
            log.setActionTimestamp(timestamp.toLocalDateTime());
        }

        return log;
    }

    /**
     * Map all rows of a ResultSet to a List of UserActivityLog objects
     */
    public static List<UserActivityLog> mapList(ResultSet rs) throws SQLException {
        List<UserActivityLog> logs = new ArrayList<>();
        while (rs.next()) {
            logs.add(map(rs));
        }
        return logs;
    }
}
