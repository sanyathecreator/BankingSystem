package org.example.service;

import java.util.ArrayList;
import java.util.List;

import org.example.model.Customer;
import org.example.model.User;
import org.example.repository.UserRepository;

public class AdminService {
    private UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userRepository.getAllUsers().values());
    }

    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }

    public void changeBalance(Customer customer, double amount) {
        customer.setBalance(amount);
        userRepository.saveUser(customer);
    }

    public List<User> findUserByUsername(String partialUsername) {
        List<User> matchingUsers = new ArrayList<>();
        String searchTerm = partialUsername.toLowerCase();

        for (User user : userRepository.getAllUsers().values()) {
            if (user.getUsername().toLowerCase().contains(searchTerm)) {
                matchingUsers.add(user);
            }
        }

        return matchingUsers;
    }
}
