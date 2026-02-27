package org.example.Test;

import org.example.Services.AuthService;

import java.util.Map;

public class AuthServiceTest {

    public static void main(String[] args) {
        AuthService authService = new AuthService();

        // Test 1: Correct username, password, and role
        Map<String, Object> login1 = authService.login("john_doe", "password123", "Admin");
        System.out.println("Login Test 1 (Correct): " + login1.get("message") + " | Success: " + login1.get("success"));

        // Test 2: Correct username, wrong password
        Map<String, Object> login2 = authService.login("john_doe", "wrongpass", "Admin");
        System.out.println("Login Test 2 (Wrong Password): " + login2.get("message") + " | Success: " + login2.get("success"));

        // Test 3: Correct username and password, wrong role
        Map<String, Object> login3 = authService.login("john_doe", "password123", "Receptionist");
        System.out.println("Login Test 3 (Wrong Role): " + login3.get("message") + " | Success: " + login3.get("success"));

        // Test 4: Non-existing username
        Map<String, Object> login4 = authService.login("nonexistent", "anyPass", "Admin");
        System.out.println("Login Test 4 (User Not Found): " + login4.get("message") + " | Success: " + login4.get("success"));
    }
}