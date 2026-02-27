package org.example.Exceptions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Stack;

public class Exception {

    public enum ExceptionType {
        DAO, SYSTEM, DATA
    }

    private int errorCode;
    private String errorMessage;
    private String errorDetails;
    private ExceptionType exceptionType;
    private LocalDateTime timestamp;

    // File path for log
    private static final String LOG_FILE_PATH = "exceptions.log";

    // Stack to store exceptions in memory
    private static final Stack<Exception> exceptionStack = new Stack<>();

    // Constructor
    public Exception(int errorCode, String errorMessage, String errorDetails, ExceptionType exceptionType) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
        this.exceptionType = exceptionType;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public int getErrorCode() { return errorCode; }
    public String getErrorMessage() { return errorMessage; }
    public String getErrorDetails() { return errorDetails; }
    public ExceptionType getExceptionType() { return exceptionType; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "[" + timestamp + "] Type: " + exceptionType +
                ", Code: " + errorCode +
                ", Message: " + errorMessage +
                ", Details: " + errorDetails;
    }

    // ======= Add exception to stack AND log to file =======
    public static void pushException(int code, String message, String details, ExceptionType type) {
        Exception exception = new Exception(code, message, details, type);
        exceptionStack.push(exception);
        writeToLogFile(exception);
    }

    // Write exception to log file
    private static void writeToLogFile(Exception exception) {
        try {
            File logFile = new File(LOG_FILE_PATH);
            // Create file if it does not exist
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            // Append to file
            try (FileWriter fw = new FileWriter(logFile, true);
                 PrintWriter pw = new PrintWriter(fw)) {
                pw.println(exception.toString());
            }

        } catch (IOException e) {
            System.err.println("Failed to write exception to log file: " + e.getMessage());
        }
    }

    // Peek latest exception
    public static Exception peekException() {
        return exceptionStack.isEmpty() ? null : exceptionStack.peek();
    }

    // Pop latest exception
    public static Exception popException() {
        return exceptionStack.isEmpty() ? null : exceptionStack.pop();
    }

    // Get all exceptions
    public static Stack<Exception> getAllExceptions() {
        return exceptionStack;
    }

    // Get exceptions filtered by type
    public static Stack<Exception> getExceptionsByType(ExceptionType type) {
        Stack<Exception> filtered = new Stack<>();
        for (Exception em : exceptionStack) {
            if (em.getExceptionType() == type) filtered.push(em);
        }
        return filtered;
    }

    // Clear all exceptions
    public static void clearExceptions() {
        exceptionStack.clear();
    }
}