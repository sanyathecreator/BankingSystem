package org.example.service;

import org.example.model.Customer;
import org.example.repository.UserRepository;

public class CustomerService {
    private UserRepository userRepository;

    public CustomerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void checkBalance(Customer customer) {
        System.out.println("Your balance: $" + String.format("%.2f", customer.getBalance()));
    }

    public void transfer(Customer customer, double amount) {
        if (customer.getBalance() < amount) {
            System.out.println("You don't have enough money on your balance!");
            return;
        }
        customer.setBalance(customer.getBalance() - amount);
        System.out.println(
                "Transferred: $" + String.format("%.2f", amount) +
                        "\nYour balance: $" + String.format("%.2f", customer.getBalance()));
        userRepository.saveUser(customer);
    }
}
