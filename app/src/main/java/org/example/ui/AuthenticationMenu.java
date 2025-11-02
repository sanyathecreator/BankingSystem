package org.example.ui;

import java.util.Scanner;

import org.example.service.AuthenticationService;
import org.example.model.User;

public class AuthenticationMenu extends BaseMenu {

    public static User welcomeMenu(Scanner scanner, AuthenticationService authService) {
        User user;
        clearConsole();
        System.out.println("=== Welcome to Maze Bank ===\n");
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Registration");
            System.out.println("0. Exit");
            int choice = validateInput(scanner, "Choose option: ", 0, 2);

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
                    clearConsole();
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void registrationMenu(Scanner scanner, AuthenticationService authService) {
        clearConsole();
        String username = validateInput(scanner, "Username: ");
        if (authService.userExists(username)) {
            System.out.println("This username is already taken!\n");
            return;
        }

        String password = validateInput(scanner, "Password: ");
        String confirmPassword = validateInput(scanner, "Confirm password: ");

        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords don't match!\n");
            return;
        }

        authService.register(username, password);
        clearConsole();
        System.out.println("Registration successful! You can now login.\n");
    }

    private static User loginMenu(Scanner scanner, AuthenticationService authService) {
        clearConsole();
        String username = validateInput(scanner, "Username: ");
        String password = validateInput(scanner, "Password: ");

        User user = authService.login(username, password);
        if (user == null) {
            System.out.println("Incorrect username or password\n");
        }
        return user;
    }

}
