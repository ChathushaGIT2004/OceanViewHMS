package org.example.Services;
 
import org.example.DTO.ResponseMessageDTO;
import org.example.DTO.UserDTO;
import org.example.Models.User.User;
import org.example.Util.SessionManager;
import org.example.dao.UserDAO;
import org.example.dao.impl.UserDAOImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.Util.PasswordUtil.hashPassword;

public class UserService {

    private final UserDAO userDAO = new UserDAOImpl();

    // =============================
    // BASIC CRUD
    // =============================

     
    public UserDTO getUserById(int id) {
        User user = userDAO.findById(id);
        return convertToDTO(user);
    }

    // Fetch user info using session token
    public UserDTO getUserByToken(String token) {

        Integer userId = SessionManager.getUserId(token);

        if (userId == null) {
            throw new RuntimeException("Invalid or expired token");
        }

        User user = userDAO.findById(userId);

        return convertToDTO(user);
    }

    
    public List<UserDTO> getAllUsers() {
        return userDAO.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ResponseMessageDTO createUser(UserDTO dto) {

        try {
            User user = new User();
            user.setUsername(dto.getUsername());
            user.setFullName(dto.getFullName());
            user.setRole(dto.getRole());
            user.setEmail(dto.getEmail());
            user.setStatus(dto.getStatus());

            // Save user
            userDAO.save(user);

            // Return success message
            return new ResponseMessageDTO(true, "User created successfully");

        } catch (Exception e) {
            // Return error message in DTO
            return new ResponseMessageDTO(false, "Failed to create user: " + e.getMessage());
        }
    }

    
    public void updateUser(UserDTO dto) {

        User user = userDAO.findById(dto.getUserID());
        if (user == null)
            throw new RuntimeException("User not found");

        user.setUsername(dto.getUsername());
        user.setFullName(dto.getFullName());
        user.setRole(dto.getRole());
        user.setEmail(dto.getEmail());
        user.setStatus(dto.getStatus());

        userDAO.update(user);
    }

    
    public void deleteUser(int id) {
        userDAO.delete(id);
    }

    // =============================
    // PASSWORD UPDATE USING TOKEN
    // =============================

    
    public void updatePassword(String token, String newPassword) {

        Integer userId = SessionManager.getUserId(token);

        if (userId == null)
            throw new RuntimeException("Invalid or Expired Token");

        User user = userDAO.findById(userId);

        if (user == null)
            throw new RuntimeException("User not found");


        user.setPasswordHash(hashPassword(newPassword));

        userDAO.update(user);
    }

    // =============================
    // HELPERS
    // =============================

    private UserDTO convertToDTO(User user) {
        if (user == null) return null;

        return new UserDTO(
                user.getUserID(),
                user.getUsername(),
                user.getFullName(),
                user.getRole(),
                user.getEmail(),
                user.getStatus()
        );
    }
}