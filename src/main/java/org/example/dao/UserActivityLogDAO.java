package org.example.dao;


import java.util.List;
import org.example.Models.User.UserActivityLog;

public interface UserActivityLogDAO {
    UserActivityLog findById(int activityID);
    List<UserActivityLog> findByUserId(int userID);
    List<UserActivityLog> findAll();
    void save(UserActivityLog log);
    void delete(int activityID);
}
