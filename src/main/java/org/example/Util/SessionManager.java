package org.example.Util;

import org.example.Models.Session;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {

    private static Map<String, Session> sessions = new HashMap<>();

    public static String createSession(int userId) {
        String token = UUID.randomUUID().toString();

        Session session = new Session();
        session.setToken(token);
        session.setUserId(userId);
        session.setCreatedAt(LocalDateTime.now());

        sessions.put(token, session);
        return token;
    }

    public static Session getSession(String token) {
        return sessions.get(token);
    }

    public static void invalidateSession(String token) {
        sessions.remove(token);
    }
}