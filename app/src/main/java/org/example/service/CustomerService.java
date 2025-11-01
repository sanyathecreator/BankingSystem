package org.example.service;

import java.util.ArrayList;
import java.util.List;

import org.example.model.Customer;
import org.example.model.Transaction;
import org.example.repository.TransactionRepository;
import org.example.repository.UserRepository;

public class CustomerService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public CustomerService(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
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

    public List<Transaction> getTransactionsHistory(Customer customer) {
        List<Transaction> allTransactions = transactionRepository.getAllTransactions();
        List<Transaction> customerTransactions = new ArrayList<>();
        for (Transaction transaction : allTransactions) {
            if (transaction.getSenderUsername().equals(customer.getUsername())
                    || transaction.getReceiverUsername().equals(customer.getUsername())) {
                customerTransactions.add(transaction);
            }
        }

        return customerTransactions;
    }
}
