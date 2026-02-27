package org.example.dao.impl;



import org.example.Config.DBConnection;
import org.example.Mappers.UserActivityLogMapper;
import org.example.Models.User.UserActivityLog;
import org.example.dao.UserActivityLogDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserActivityLogDAOImpl implements UserActivityLogDAO {

    private Connection conn;

    public UserActivityLogDAOImpl() {
        try {
            conn = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserActivityLog findById(int activityID) {
        String sql = "SELECT * FROM UserActivityLog WHERE ActivityID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, activityID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return UserActivityLogMapper.map(rs); // mapper handles ResultSet -> UserActivityLog
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserActivityLog> findByUserId(int userID) {
        String sql = "SELECT * FROM UserActivityLog WHERE UserID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            return UserActivityLogMapper.mapList(rs); // mapper handles ResultSet -> List<UserActivityLog>
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserActivityLog> findAll() {
        String sql = "SELECT * FROM UserActivityLog";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return UserActivityLogMapper.mapList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(UserActivityLog log) {
        String sql = "INSERT INTO UserActivityLog(UserID, ActionType, ActionTarget, TargetReferenceID, ActionDetails) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, log.getUserID());
            ps.setString(2, log.getActionType());
            ps.setString(3, log.getActionTarget());
            if (log.getTargetReferenceID() != -1) {
                ps.setInt(4, log.getTargetReferenceID());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            ps.setString(5, log.getActionDetails());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int activityID) {
        String sql = "DELETE FROM UserActivityLog WHERE ActivityID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, activityID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
