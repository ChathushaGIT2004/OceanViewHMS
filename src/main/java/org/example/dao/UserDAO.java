package org.example.dao;

import org.example.Models.User;

public interface UserDAO {

        User findByUsername(String username);

}
