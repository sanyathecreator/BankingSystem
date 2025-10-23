package org.example;

import java.util.Scanner;

import org.example.model.User;
import org.example.service.AuthenticationService;
import org.example.service.BankingService;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);
    public static final AuthenticationService authService = new AuthenticationService();
    public static final BankingService bankingService = new BankingService();

    public static void main(String[] args) {
        System.out.println("=== Welcome to Maze Bank ===\n");
        while (true) {
            System.out.println("1. Login");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> userMenu();
                case 0 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public static void userMenu() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = authService.login(username, password);
        if (user == null) {
            System.out.println("Incorrect username or password");
            return;
        }

        System.out.println("Welcome, " + user.getUsername() + "\n");
        while (true) {
            System.out.println("1. Check balance");
            System.out.println("2. Transfer money");
            System.out.println("0. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> bankingService.checkBalance(user);
                case 2 -> {
                    System.out.print("Amount: ");
                    double amount = scanner.nextDouble();
                    bankingService.transfer(user, amount);
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
