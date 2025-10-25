package org.example.ui;

import java.util.Scanner;

import org.example.service.AuthenticationService;
import org.example.model.User;

public class WelcomeMenu {

    public static User welcomeMenu(Scanner scanner, AuthenticationService authService) {
        User user;
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
                    user = loginMenu(scanner, authService);
                    if (user == null) {
                        continue;
                    }
                    return user;
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

    private static void registrationMenu(Scanner scanner, AuthenticationService authService) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        if (authService.userExists(username)) {
            System.out.println("This username is already taken!\n");
            return;
        }

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

    private static User loginMenu(Scanner scanner, AuthenticationService authService) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = authService.login(username, password);
        if (user == null) {
            System.out.println("Incorrect username or password");
        }
        return user;
    }

}
