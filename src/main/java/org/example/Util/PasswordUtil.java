package org.example.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtil {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:,.<>?";
    private static final String PASSWORD_ALLOW = CHAR_LOWER + CHAR_UPPER + DIGITS + SYMBOLS;

    private static final SecureRandom random = new SecureRandom();

    // Hash a password using SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Verify password against stored hash
    public static boolean verifyPassword(String inputPassword, String storedHash) {
        return hashPassword(inputPassword).equals(storedHash);
    }

    // Generate a random secure password of given length
    public static String generateRandomPassword(int length) {
        if (length < 6) { // minimum secure length
            throw new IllegalArgumentException("Password length should be at least 6 characters");
        }

        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(PASSWORD_ALLOW.length());
            password.append(PASSWORD_ALLOW.charAt(index));
        }
        return password.toString();
    }

    // Example usage
    public static void main(String[] args) {
        String randomPassword = generateRandomPassword(12);
        System.out.println("Random Password: " + randomPassword);
        System.out.println("Hashed Password: " + hashPassword(randomPassword));
    }
}