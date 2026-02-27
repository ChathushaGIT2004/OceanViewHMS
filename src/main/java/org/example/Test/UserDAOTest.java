package org.example.Test;


import org.example.dao.UserDAO;
import org.example.dao.impl.UserDAOImpl;
import org.example.Models.User.User;

import java.util.List;

public class UserDAOTest {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAOImpl();

        // ====== CREATE TEST ======
        User newUser = new User();
        newUser.setUsername("cj_admin");
        newUser.setPasswordHash("hashed123");
        newUser.setFullName("CJ Big Boss");
        newUser.setRole("ADMIN");
        newUser.setEmail("cj@example.com");
        newUser.setStatus("ACTIVE");

        userDAO.save(newUser);
        System.out.println("User saved successfully");


        // ====== FIND ALL TEST ======
        List<User> users = userDAO.findAll();
        System.out.println("\nAll Users:");
        for (User u : users) {
            System.out.println(u.getUserID() + " | " + u.getUsername());
        }


        // ====== FIND BY USERNAME TEST ======
        User foundByUsername = userDAO.findByUsername("cj_admin");
        if (foundByUsername != null) {
            System.out.println("\nFound by username: " + foundByUsername.getFullName());
        }


        // ====== FIND BY ID TEST ======
        if (foundByUsername != null) {
            User foundById = userDAO.findById(foundByUsername.getUserID());
            System.out.println("Found by ID: " + foundById.getUsername());


            // ====== UPDATE TEST ======
            foundById.setFullName("CJ Updated Boss");
            foundById.setStatus("INACTIVE");
            userDAO.update(foundById);
            System.out.println("User updated");


            // ====== DELETE TEST ======
            userDAO.delete(foundById.getUserID());
            System.out.println("User deleted");
        }
    }
}