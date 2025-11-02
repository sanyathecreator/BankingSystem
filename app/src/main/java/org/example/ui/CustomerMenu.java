package org.example.ui;

import java.util.List;
import java.util.Scanner;

import org.example.service.CustomerService;
import org.example.service.TransactionService;
import org.example.service.TransactionStatus;
import org.example.util.ConsoleColors;
import org.example.model.Customer;
import org.example.model.Transaction;

public class CustomerMenu extends BaseMenu {

    public static void mainMenu(Customer customer, Scanner scanner, CustomerService customerService,
            TransactionService transactionService) {
        clearConsole();
        System.out.println("Welcome, " + customer.getUsername() + "\n");
        while (true) {
            System.out.println("1. Check balance");
            System.out.println("2. Transfer money");
            System.out.println("3. Display transaction history");
            System.out.println("0. Exit");
            int choice = validateInput(scanner, "Choose option: ", 0, 3);

            switch (choice) {
                case 1 -> {
                    clearConsole();
                    customerService.checkBalance(customer);
                }
                case 2 -> {
                    clearConsole();
                    transactionMenu(customer, scanner, transactionService);
                }
                case 3 -> {
                    clearConsole();
                    displayTransactionHistory(customer, customerService);
                }
                case 0 -> {
                    clearConsole();
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
            System.out.println();
        }
    }

    public static void displayTransactionHistory(Customer customer, CustomerService customerService) {
        List<Transaction> transactions = customerService.getTransactionsHistory(customer);
        System.out.println("Transaction History:");
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction transaction : transactions) {
                if (transaction.getReceiverUsername().equals(customer.getUsername()))
                    System.out.println(ConsoleColors.green(transaction.toString()));
                if (transaction.getSenderUsername().equals(customer.getUsername()))
                    System.out.println(ConsoleColors.red(transaction.toString()));
            }
        }
    }

    public static void transactionMenu(Customer customer, Scanner scanner, TransactionService transactionService) {
        String receiverUsername = validateInput(scanner, "Input receiver username: ");
        double amount = validateInput(scanner, "Input amount: $", 0.01, Double.MAX_VALUE);

        TransactionStatus requestResult = transactionService.makeTransaction(customer, receiverUsername, amount);
        System.out.println();
        switch (requestResult) {
            case CUSTOMER_NOT_FOUND:
                System.out.println("Customer " + receiverUsername + " not found!");
                break;
            case CUSTOMER_WRONG_TYPE:
                System.out.println("You can send money only to customers of our bank!");
                break;
            case INVALID_AMOUNT:
                System.out.println("Invalid amount!");
                System.out.println("Your balance: $" + customer.getBalance());
                break;
            case NOT_ENOUGH_MONEY:
                System.out.println("You don't have enough money!");
                System.out.println("Your balance: $" + customer.getBalance());
                break;
            case SAME_ACCOUNT:
                System.out.println("You're trying to send money to yourself!");
                break;
            case SUCCESS:
                System.out.println("Transaction completed successfully!");
                System.out.println("Your new balance: $" + customer.getBalance());
                break;
            default:
                System.out.println("Try again...");
                break;
        }
    }
}
