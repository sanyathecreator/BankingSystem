package org.example.service;

import org.example.model.User;

public class AuthenticationService {

    public User login(String username, String password) {
        if (password.equals("1234")) {
            return new User(username, password, 3000);
        }
        return null;
    }
}
