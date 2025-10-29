package org.example.service;

import org.example.model.Customer;
import org.example.model.User;
import org.example.repository.UserRepository;

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
        if (!user.getPassword().equals(password)) {
            System.out.println("Wrong password!");
            return null;
        }
        return user;
    }

    public boolean userExists(String username) {
        return userRepository.userExists(username);
    }

    public void register(String username, String password) {
        Customer customer = new Customer(username, password, 1000);
        userRepository.saveUser(customer);
    }
}
