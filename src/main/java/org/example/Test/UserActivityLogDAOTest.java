package org.example.Test;

import org.example.Models.User.UserActivityLog;
import org.example.dao.UserActivityLogDAO;
import org.example.dao.impl.UserActivityLogDAOImpl;

import java.time.LocalDateTime;
import java.util.List;

public class UserActivityLogDAOTest {

    public static void main(String[] args) {

        UserActivityLogDAO logDAO = new UserActivityLogDAOImpl();

        // ====== CREATE TEST ======
        UserActivityLog log = new UserActivityLog();
        log.setUserID(2);
        log.setActionType("CREATE");
        log.setActionTarget("Guest");
        log.setTargetReferenceID(1); // example target ID
        log.setActionDetails("Created new guest with ID 101");
        log.setActionTimestamp(LocalDateTime.now());

        logDAO.save(log);
        System.out.println("User activity log saved successfully");

        // ====== FIND ALL TEST ======
        List<UserActivityLog> logs = logDAO.findAll();
        System.out.println("\nAll User Activity Logs:");
        for (UserActivityLog l : logs) {
            System.out.println(l.getActivityID() + " | User " + l.getUserID() + " | " + l.getActionType() + " | " + l.getActionTarget());
        }

        // ====== FIND BY ID TEST ======
        if (!logs.isEmpty()) {
            int testID = logs.get(0).getActivityID();
            UserActivityLog byId = logDAO.findById(testID);
            System.out.println("\nFound by ID: " + byId.getActivityID() + " | Action: " + byId.getActionType());
        }

        // ====== FIND BY USER ID TEST ======
        List<UserActivityLog> userLogs = logDAO.findByUserId(1);
        System.out.println("\nLogs for User ID 1:");
        for (UserActivityLog l : userLogs) {
            System.out.println(l.getActivityID() + " | Action: " + l.getActionType() + " | Target: " + l.getActionTarget());
        }

        // ====== DELETE TEST ======
        if (!logs.isEmpty()) {
            int deleteID = logs.get(0).getActivityID();
            logDAO.delete(deleteID);
            System.out.println("\nDeleted log with ID " + deleteID);
        }
    }
}