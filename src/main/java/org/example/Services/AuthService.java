package org.example.Services;

import org.example.Models.User.User;
import org.example.Models.User.UserActivityLog;
import org.example.Util.SessionManager;
import org.example.dao.UserDAO;
import org.example.dao.UserActivityLogDAO;
import org.example.dao.impl.UserDAOImpl;
import org.example.dao.impl.UserActivityLogDAOImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.example.Util.PasswordUtil.hashPassword;

public class AuthService {

    private final UserDAO userDAO;
    private final UserActivityLogDAO activityLogDAO;

    public AuthService() {
        this.userDAO = new UserDAOImpl();
        this.activityLogDAO = new UserActivityLogDAOImpl();
    }

    /**
     * Logs in the user and creates a session using SessionManager
     */
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> result = new HashMap<>();
        boolean success = false;
        String message = "";
        String token = null;

        User user = userDAO.findByUsername(username);




        if (user != null) {
            if (user.getPasswordHash().equals(hashPassword( password))) {

                    success = true;
                    message = "Login successful";


                    token = SessionManager.createSession(user.getUserID(),user.getRole());
                    System.out.println("Authservise session token"+token);


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

        if (success) {
            result.put("token", token);
            result.put("userID", user.getUserID());
        }

        return result;
    }


    public void logout(String token) {
         UserActivityLogService.getInstance().log(token,"LOGOUT","AuthService",0,"LOgout Successfully");
        SessionManager.invalidateSession(token);
    }

    private void saveLoginActivity(User user, boolean success, String message) {
        UserActivityLog log = new UserActivityLog();
        log.setUserID(user.getUserID());
        log.setActionType("Login");
        log.setActionTarget("AuthService");
        log.setTargetReferenceID(0);
        log.setActionDetails(message);
        log.setActionTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        try {
            activityLogDAO.save(log);
        } catch (Exception e) {
            System.err.println("Failed to save login activity: " + e.getMessage());
        }
    }
}