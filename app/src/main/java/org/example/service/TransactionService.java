package org.example.service;

import org.example.model.Customer;
import org.example.model.Transaction;
import org.example.model.User;
import org.example.repository.TransactionRepository;
import org.example.repository.UserRepository;

public class TransactionService {
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public boolean makeTransaction(String senderUsername, String receiverUsername, double amount) {
        if (!validateCustomer(senderUsername) || !validateCustomer(receiverUsername)) {
            System.out.println("Customer doesn't exixst!");
            return false;
        }
        Customer sender = (Customer) userRepository.getUser(senderUsername);
        Customer receiver = (Customer) userRepository.getUser(receiverUsername);

        if (!isEnoughMoney(sender, amount)) {
            System.out.println("Customer doesn't have enough money!");
            return false;
        }
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        transactionRepository.saveTransaction(new Transaction(senderUsername, receiverUsername, amount));
        return true;
    }

    private boolean isEnoughMoney(Customer customer, double amount) {
        return customer.getBalance() >= amount;
    }

    private boolean validateCustomer(String username) {
        if (!userRepository.userExists(username)) {
            return false;
        }
        User user = userRepository.getUser(username);
        return user instanceof Customer;
    }
}
