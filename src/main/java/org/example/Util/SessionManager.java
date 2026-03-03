package org.example.Util;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {

    // =============================
    // INNER SESSION CLASS
    // =============================
    private static class SessionData {
        int userId;
        String role;
        LocalDateTime expiresAt;

        SessionData(int userId, String role, LocalDateTime expiresAt) {
            this.userId = userId;
            this.role = role;
            this.expiresAt = expiresAt;
        }
    }

    // token -> session
    private static final Map<String, SessionData> sessions = new HashMap<>();

    private static final int SESSION_HOURS = 2;

    // =============================
    // CREATE SESSION
    // =============================
    public static String createSession(int userId, String role) {

        String token = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(SESSION_HOURS);

        sessions.put(token, new SessionData(userId, role, expiresAt));

        return token;
    }

    // =============================
    // VALIDATE TOKEN
    // =============================
    public static boolean isValidToken(String token) {

        if (token == null || token.isEmpty())
            return false;

        SessionData data = sessions.get(token);

        if (data == null)
            return false;

        if (data.expiresAt.isBefore(LocalDateTime.now())) {
            sessions.remove(token); // auto remove expired
            return false;
        }

        return true;
    }

    // =============================
    // GET USER ID
    // =============================
    public static Integer getUserId(String token) {

        if (!isValidToken(token))
            return null;

        return sessions.get(token).userId;
    }

    // =============================
    // GET ROLE
    // =============================
    public static String getRole(String token) {

        if (!isValidToken(token))
            return null;

        return sessions.get(token).role;
    }

    // =============================
    // GET EXPIRE TIME
    // =============================
    public static LocalDateTime getExpireTime(String token) {

        if (!isValidToken(token))
            return null;

        return sessions.get(token).expiresAt;
    }

    // =============================
    // INVALIDATE SESSION
    // =============================
    public static void invalidateSession(String token) {
        sessions.remove(token);
    }
}