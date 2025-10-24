package org.example.service;

import org.example.model.Customer;
import org.example.repository.UserRepository;

public class AuthenticationService {
    private UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Customer login(String username, String password) {
        // TODO: Look if username is in users list and return error if not
        // TODO: Check if the passwords match anr return error if not
        if (password.equals("1234")) {
            return new Customer(username, password, 3000);
        }
        return null;
    }

    public void register(String username, String password) {
        // TODO Create new user with username and passord and add him to users list
    }
}
