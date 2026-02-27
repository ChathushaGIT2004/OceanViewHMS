package org.example.Services;

import org.example.Models.User.User;
import org.example.Models.User.UserActivityLog;
import org.example.dao.UserDAO;
import org.example.dao.UserActivityLogDAO;
import org.example.dao.impl.UserDAOImpl;
import org.example.dao.impl.UserActivityLogDAOImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private UserDAO userDAO;
    private UserActivityLogDAO activityLogDAO;

    public AuthService() {
        this.userDAO = new UserDAOImpl();
        this.activityLogDAO = new UserActivityLogDAOImpl();
    }

    public Map<String, Object> login(String username, String password, String role) {
        Map<String, Object> result = new HashMap<>();
        boolean success = false;
        String message = "";

        User user = userDAO.findByUsername(username);

        if (user != null) {
            if (user.getPasswordHash().equals(password)) {
                if (user.getRole().equalsIgnoreCase(role)) {
                    success = true;
                    message = "Login successful";
                } else {
                    message = "Login failed - Incorrect role";
                }
            } else {
                message = "Login failed - Invalid password";
            }

            saveLoginActivity(user, success, message);

        } else {
            message = "Login failed - User not found";
            System.out.println(message);
        }

        result.put("success", success);
        result.put("message", message);
        return result;
    }


    private void saveLoginActivity(User user, boolean success, String message) {
        UserActivityLog log = new UserActivityLog();
        log.setUserID(user.getUserID());
        log.setActionType("Login");
        log.setActionTarget("AuthenticationService");
        log.setTargetReferenceID(0);
        log.setActionDetails(message);
        log.setActionTimestamp(LocalDateTime.now());

        try {
            activityLogDAO.save(log);
        } catch (Exception e) {
            System.err.println("Failed to save login activity: " + e.getMessage());
        }
    }
}