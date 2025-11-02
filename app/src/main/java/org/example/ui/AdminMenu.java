package org.example.ui;

import java.util.List;
import java.util.Scanner;

import org.example.model.Admin;
import org.example.model.Transaction;
import org.example.model.User;
import org.example.service.AdminService;
import org.example.service.TransactionStatus;
import org.example.util.ConsoleColors;

public class AdminMenu extends BaseMenu {

    public static void mainMenu(Admin admin, Scanner scanner, AdminService adminService) {
        clearConsole();
        System.out.println("Welcome, " + admin.getUsername() + "\n");
        while (true) {
            List<User> users = adminService.getAllUsers();
            List<Transaction> transactions = adminService.getAllTransactions();
            System.out.println("1. Display all users");
            System.out.println("2. Find user by username");
            System.out.println("3. Display transaction history");
            System.out.println("4. Log transactions");
            System.out.println("0. Exit");
            int choice = validateInput(scanner, "Choose option: ", 0, 4);

            switch (choice) {
                case 0 -> {
                    clearConsole();
                    return;
                }
                case 1 -> {
                    displayUsers(users, adminService);
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    clearConsole();
                }
                case 2 -> {
                    findMenu(adminService, scanner);
                    clearConsole();
                }
                case 3 -> {
                    displayTransactions(transactions);
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    clearConsole();
                }
                case 4 -> {
                    clearConsole();
                    adminService.logTransactions();
                    System.out.println();
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void displayUsers(List<User> users, AdminService adminService) {
        clearConsole();
        System.out.println(
                "Users list " + ConsoleColors.green(String.format("$%.2f: ", adminService.getAllAmountOfMoney(users))));
        for (int i = 0; i < users.size(); i++) {
            System.out.println(i + ". " + users.get(i).toString());
        }
    }

    private static void displayTransactions(List<Transaction> transactions) {
        clearConsole();
        System.out.println("Transaction history: ");
        for (int i = 0; i < transactions.size(); i++) {
            System.out.println(i + ". " + transactions.get(i).toString());
        }
    }

    private static void findMenu(AdminService adminService, Scanner scanner) {
        clearConsole();
        String partialUsername = validateInput(scanner, "Username: ");
        List<User> matchedUsers = adminService.findUserByUsername(partialUsername);
        clearConsole();
        System.out.println("Matched users:");
        displayUsers(matchedUsers, adminService);

        if (matchedUsers.isEmpty()) {
            System.out.println("\nNo users found.");
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
            return;
        }

        System.out.println("\n1. Select user");
        System.out.println("0. Back");

        int choice = validateInput(scanner, "Choose option: ", 0, 1);
        switch (choice) {
            case 1 -> {
                selectMenu(matchedUsers, adminService, scanner);
            }
            default -> {
                return;
            }
        }
    }

    private static void selectMenu(List<User> users, AdminService adminService, Scanner scanner) {
        clearConsole();
        displayUsers(users, adminService);
        int index = validateInput(scanner, "Select user (enter number): ", 0, users.size() - 1);
        User selectedUser = users.get(index);

        clearConsole();
        System.out.println("Selected user: " + selectedUser.toString());
        System.out.println("\n1. Change balance");
        System.out.println("2. Delete user");
        System.out.println("0. Back");

        int choice = validateInput(scanner, "Choose option: ", 0, 2);
        switch (choice) {
            case 1 -> {
                double amount = validateInput(scanner, "Amount: $", -Double.MAX_VALUE, Double.MAX_VALUE);
                TransactionStatus requestResult = adminService.changeBalance(selectedUser, amount);
                System.out.println();
                if (requestResult == TransactionStatus.CUSTOMER_WRONG_TYPE)
                    System.out.println("You can change balance only for customers!");
                else
                    System.out.println("Balance changed successfully!");
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
            case 2 -> {
                if (confirmAction(scanner, "Are you sure you want to delete user " + selectedUser.getUsername() + "? ")) {
                    adminService.deleteUser(selectedUser);
                    System.out.println("\nUser " + selectedUser.getUsername() + " deleted!");
                } else {
                    System.out.println("\nDeletion cancelled.");
                }
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
            case 0 -> {
                return;
            }
            default -> {
                return;
            }
        }
    }
}
