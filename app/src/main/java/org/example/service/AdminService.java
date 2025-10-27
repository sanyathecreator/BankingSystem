package org.example.service;

import java.util.ArrayList;
import java.util.List;

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
}
