package org.example.service;

import java.util.ArrayList;
import java.util.List;

import org.example.model.Customer;
import org.example.model.Transaction;
import org.example.model.User;
import org.example.repository.TransactionRepository;
import org.example.repository.UserRepository;

public class AdminService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public AdminService(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userRepository.getAllUsers().values());
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactionRepository.getAllTransactions());
    }

    public void logTransactions() {
        transactionRepository.logTransactions();
    }

    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }

    public TransactionStatus changeBalance(User user, double amount) {
        if (!(user instanceof Customer))
            return TransactionStatus.CUSTOMER_WRONG_TYPE;
        Customer customer = (Customer) user;
        customer.setBalance(amount);
        userRepository.saveUser(customer);
        return TransactionStatus.SUCCESS;
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
