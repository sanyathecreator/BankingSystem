package org.example.service;

import org.example.model.Customer;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.util.PasswordHasher;

public class AuthenticationService {
    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) {
        User user;

        if (!userRepository.userExists(username)) {
            System.out.println("Customer doesn't exists!");
            return null;
        } else {
            user = userRepository.getUser(username);
        }
        if (!PasswordHasher.verifyPassword(password, user.getPassword())) {
            System.out.println("Wrong password!");
            return null;
        }
        return user;
    }

    public boolean userExists(String username) {
        return userRepository.userExists(username);
    }

    public void register(String username, String password) {
        String hashedPassword = PasswordHasher.hashPassword(password);
        Customer customer = new Customer(username, hashedPassword, 1000);
        userRepository.saveUser(customer);
    }
}
