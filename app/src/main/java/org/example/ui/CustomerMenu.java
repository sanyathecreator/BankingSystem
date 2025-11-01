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
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> customerService.checkBalance(customer);
                case 2 -> transactionMenu(customer, scanner, transactionService);
                case 3 -> displayTransactionHistory(customer, customerService);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public static void displayTransactionHistory(Customer customer, CustomerService customerService) {
        List<Transaction> transactions = customerService.getTransactionsHistory(customer);
        System.out.println("History:");
        for (Transaction transaction : transactions) {
            if (transaction.getReceiverUsername().equals(customer.getUsername()))
                System.out.println(ConsoleColors.green(transaction.toString()));
            if (transaction.getSenderUsername().equals(customer.getUsername()))
                System.out.println(ConsoleColors.red(transaction.toString()));
        }
    }

    public static void transactionMenu(Customer customer, Scanner scanner, TransactionService transactionService) {
        System.out.print("Input receiver username: ");
        String receiverUsername = scanner.nextLine();
        System.out.print("Input amount: $");
        double amount = scanner.nextDouble();
        TransactionStatus requestResult = transactionService.makeTransaction(customer, receiverUsername, amount);
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
                break;
            case SAME_ACCOUNT:
                System.out.println("You're trying to send money to yourself!");
                break;
            case SUCCESS:
                System.out.println("Transaction completed=)");
                System.out.println("Your balance: $" + customer.getBalance());
                break;
            default:
                System.out.println("Try again...");
                break;
        }
    }
}
