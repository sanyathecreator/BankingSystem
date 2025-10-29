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

    public TransactionStatus makeTransaction(String senderUsername, String receiverUsername, double amount) {
        TransactionStatus validation = validateTransaction(senderUsername, receiverUsername, amount);
        if (validation != TransactionStatus.SUCCESS)
            return validation;
        Customer sender = (Customer) userRepository.getUser(senderUsername);
        Customer receiver = (Customer) userRepository.getUser(receiverUsername);

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        userRepository.saveUser(sender);
        userRepository.saveUser(receiver);

        transactionRepository.saveTransaction(new Transaction(senderUsername, receiverUsername, amount));
        return TransactionStatus.SUCCESS;
    }

    private TransactionStatus validateFunds(Customer customer, double amount) {
        if (amount <= 0)
            return TransactionStatus.INVALID_AMOUNT;
        if (customer.getBalance() < amount)
            return TransactionStatus.NOT_ENOUGH_MONEY;
        return TransactionStatus.SUCCESS;
    }

    private TransactionStatus validateTransaction(String senderUsername, String receiverUsername, double amount) {
        TransactionStatus senderValidation = validateUser(senderUsername);
        TransactionStatus receiverValidation = validateUser(receiverUsername);
        if (senderValidation != TransactionStatus.SUCCESS)
            return senderValidation;
        if (receiverValidation != TransactionStatus.SUCCESS)
            return receiverValidation;
        if (senderUsername.equals(receiverUsername))
            return TransactionStatus.SAME_ACCOUNT;

        Customer sender = (Customer) userRepository.getUser(senderUsername);
        TransactionStatus moneyAmountValidation = validateFunds(sender, amount);
        if (moneyAmountValidation != TransactionStatus.SUCCESS)
            return moneyAmountValidation;

        return TransactionStatus.SUCCESS;
    }

    private TransactionStatus validateUser(String username) {
        User user = userRepository.getUser(username);
        if (user == null) {
            return TransactionStatus.CUSTOMER_NOT_FOUND;
        }
        if (!(user instanceof Customer)) {
            return TransactionStatus.CUSTOMER_WRONG_TYPE;
        }
        return TransactionStatus.SUCCESS;
    }
}
