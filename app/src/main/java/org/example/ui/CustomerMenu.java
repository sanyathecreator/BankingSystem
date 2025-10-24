package org.example.ui;

import java.util.Scanner;

import org.example.service.AuthenticationService;
import org.example.service.BankingService;
import org.example.model.Customer;

public class CustomerMenu {

    public static void userMenu(Scanner scanner, AuthenticationService authService, BankingService bankingService) {
        Customer customer = welcomeMenu(scanner, authService);
        mainMenu(customer, scanner, bankingService);
    }

    private static void mainMenu(Customer customer, Scanner scanner, BankingService bankingService) {
        System.out.println("Welcome, " + customer.getUsername() + "\n");
        while (true) {
            System.out.println("1. Check balance");
            System.out.println("2. Transfer money");
            System.out.println("0. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> bankingService.checkBalance(customer);
                case 2 -> {
                    System.out.print("Amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    bankingService.transfer(customer, amount);
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static Customer welcomeMenu(Scanner scanner, AuthenticationService authService) {
        Customer customer = new Customer();
        System.out.println("=== Welcome to Maze Bank ===\n");
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Registration");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    customer = loginMenu(scanner, authService);
                    if (customer == null) {
                        continue;
                    }
                    return customer;
                }
                case 2 -> registrationMenu(scanner, authService);
                case 0 -> {
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static Customer loginMenu(Scanner scanner, AuthenticationService authService) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Customer customer = authService.login(username, password);
        if (customer == null) {
            System.out.println("Incorrect username or password");
        }
        return customer;
    }

    private static void registrationMenu(Scanner scanner, AuthenticationService authService) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Confirm password: ");
        String confirmPassword = scanner.nextLine();

        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords don't match!");
            return;
        }

        authService.register(username, password);
        System.out.println("Registration successful! You can now login.\n");
    }
}
