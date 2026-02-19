package org.example.dao;


import org.example.Models.User;

import java.util.List;


public interface UserDAO {
        User findById(int userID);
        User findByUsername(String username);
        List<User> findAll();
        void save(User user);
        void update(User user);
        void delete(int userID);
}