package org.example.service;

import org.example.model.Customer;

public class AuthenticationService {

    public Customer login(String username, String password) {
        if (password.equals("1234")) {
            return new Customer(username, password, 3000);
        }
        return null;
    }
}
