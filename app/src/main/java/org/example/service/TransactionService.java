package org.example.service;

import org.example.model.Customer;
import org.example.model.Transaction;
import org.example.model.User;
import org.example.repository.TransactionRepository;
import org.example.repository.UserRepository;

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public TransactionStatus makeTransaction(Customer sender, String receiverUsername, double amount) {
        User receiverUser = userRepository.getUser(receiverUsername);
        TransactionStatus validation = validateTransaction(sender, receiverUser, amount);
        if (validation != TransactionStatus.SUCCESS)
            return validation;

        Customer receiver = (Customer) receiverUser;
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        userRepository.saveUser(sender);
        userRepository.saveUser(receiver);

        transactionRepository.saveTransaction(new Transaction(sender.getUsername(), receiverUsername, amount));
        return TransactionStatus.SUCCESS;
    }

    private TransactionStatus validateFunds(Customer customer, double amount) {
        if (amount <= 0)
            return TransactionStatus.INVALID_AMOUNT;
        if (customer.getBalance() < amount)
            return TransactionStatus.NOT_ENOUGH_MONEY;
        return TransactionStatus.SUCCESS;
    }

    private TransactionStatus validateTransaction(Customer sender, User receiver, double amount) {
        TransactionStatus receiverValidation = validateUser(receiver);
        if (receiverValidation != TransactionStatus.SUCCESS)
            return receiverValidation;
        if (sender.getUsername().equals(receiver.getUsername()))
            return TransactionStatus.SAME_ACCOUNT;

        TransactionStatus moneyAmountValidation = validateFunds(sender, amount);
        if (moneyAmountValidation != TransactionStatus.SUCCESS)
            return moneyAmountValidation;

        return TransactionStatus.SUCCESS;
    }

    private TransactionStatus validateUser(User user) {
        if (user == null) {
            return TransactionStatus.CUSTOMER_NOT_FOUND;
        }
        if (!(user instanceof Customer)) {
            return TransactionStatus.CUSTOMER_WRONG_TYPE;
        }
        return TransactionStatus.SUCCESS;
    }
}
