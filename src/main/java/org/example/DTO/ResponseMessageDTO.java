package org.example.DTO;


public class ResponseMessageDTO {
    private boolean success;
    private String message;
    private Object data; // optional, can hold any extra info

    public ResponseMessageDTO() {
    }

    public ResponseMessageDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResponseMessageDTO(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public static ResponseMessageDTO invalidToken() {
        ResponseMessageDTO dto = new ResponseMessageDTO();
        dto.setSuccess(false);
        dto.setMessage("Invalid or expired token.");
        return dto;
    }
}
