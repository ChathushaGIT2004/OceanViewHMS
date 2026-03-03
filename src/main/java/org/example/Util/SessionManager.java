package org.example.Util;

import org.example.Models.Session;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {


    // Inner class for session data
    private static class SessionData {
        int userId;
        LocalDateTime expiresAt;

        SessionData(int userId, LocalDateTime expiresAt) {
            this.userId = userId;
            this.expiresAt = expiresAt;
        }
    }

    // Map: token -> session data
    private static final Map<String, SessionData> sessions = new HashMap<>();

    // Default session duration
    private static final int SESSION_HOURS = 2;

    public static String createSession(int userId) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(SESSION_HOURS);
        sessions.put(token, new SessionData(userId, expiresAt));
        return token;
    }


    public static boolean isValidToken(String token) {
        if (token == null || token.isEmpty()) return false;

        SessionData data = sessions.get(token);
        if (data == null) return false;

        if (data.expiresAt.isBefore(LocalDateTime.now())) {
            sessions.remove(token); // auto-clean expired token
            return false;
        }

        return true;
    }

    public static Integer getUserId(String token) {
        if (!isValidToken(token)) return null;
        return sessions.get(token).userId;
    }

    /**
     * Logs out a token.
     */
    public static void invalidateSession(String token) {
        sessions.remove(token);
    }
}