package org.example.service;

import org.example.model.Customer;
import org.example.repository.UserRepository;

public class AuthenticationService {
    private UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Customer login(String username, String password) {
        Customer customer;

        if (!userRepository.userExists(username)) {
            System.out.println("Customer doesn't exists!");
            return null;
        } else {
            customer = (Customer) userRepository.getUser(username);
        }
        if (!customer.getPassword().equals(password)) {
            System.out.println("Wrong password!");
            return null;
        }
        return customer;
    }

    public void register(String username, String password) {
        Customer customer = new Customer(username, password, 1000);
        userRepository.saveUser(customer);
    }
}
