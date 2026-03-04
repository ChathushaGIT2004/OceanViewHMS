package org.example.Services;

import org.example.Models.User.UserActivityLog;
import org.example.dao.UserActivityLogDAO;
import org.example.dao.impl.UserActivityLogDAOImpl;
import org.example.Util.SessionManager;

import java.sql.Timestamp;
import java.time.LocalDateTime;


public class UserActivityLogService {

    private final UserActivityLogDAO logDAO;

    private static volatile UserActivityLogService instance;

    private UserActivityLogService() {
        this.logDAO = new UserActivityLogDAOImpl();
    }

    public static UserActivityLogService getInstance() {
        if (instance == null) {
            synchronized (UserActivityLogService.class) {
                if (instance == null) {
                    instance = new UserActivityLogService();
                }
            }
        }
        return instance;
    }


    public void saveActivity(String token, UserActivityLog log) throws Exception {

        if (!SessionManager.isValidToken(token)) {
            throw new Exception("Invalid session token");
        }

        int userID = SessionManager.getUserId(token);

        log.setUserID(userID);
        log.setActionTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        logDAO.save(log);
    }


    public void log(String token,
                    String actionType,
                    String actionTarget,
                    int targetRefID,
                    String details) {

        System.out.println("UserActivity Log method  Accessed");

        try {


            int userID = SessionManager.getUserId(token);
            System.out.println("USer Activity log ID Fount"+userID);

            UserActivityLog log = new UserActivityLog();
            log.setUserID(userID);
            log.setActionType(actionType);
            log.setActionTarget(actionTarget);
            log.setTargetReferenceID(targetRefID);
            log.setActionDetails(details);
            log.setActionTimestamp(Timestamp.valueOf(LocalDateTime.now()));

            logDAO.save(log);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
