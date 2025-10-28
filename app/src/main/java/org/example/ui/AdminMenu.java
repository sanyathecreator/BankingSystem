package org.example.ui;

import java.util.List;
import java.util.Scanner;

import org.example.model.Admin;
import org.example.model.Customer;
import org.example.model.User;
import org.example.service.AdminService;

public class AdminMenu extends BaseMenu {

    public static void mainMenu(Admin admin, Scanner scanner, AdminService adminService) {
        System.out.println("Welcome, " + admin.getUsername() + "\n");
        while (true) {
            clearConsole();
            List<User> users = adminService.getAllUsers();
            System.out.println("1. Display all users");
            System.out.println("2. Find user by username");
            System.out.println("0. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    displayUsers(users);
                    scanner.nextLine();
                }
                case 2 -> {
                    findMenu(adminService, scanner);
                    scanner.nextLine();
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void displayUsers(List<User> users) {
        clearConsole();
        System.out.println("Users list: ");
        for (int i = 0; i < users.size(); i++) {
            System.out.println(i + ". " + users.get(i).toString());
        }
    }

    private static void findMenu(AdminService adminService, Scanner scanner) {
        System.out.print("Username: ");
        String partialUsername = scanner.nextLine();
        List<User> matchedUsers = adminService.findUserByUsername(partialUsername);
        clearConsole();
        System.out.println("Matched users:");
        displayUsers(matchedUsers);
        System.out.println("\n1. Select user");
        System.out.println("0. Back");

        int choice = scanner.nextInt();
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
        displayUsers(users);
        System.out.print("Select user: ");
        int index = scanner.nextInt();
        User selectedUser = users.get(index);
        System.out.println("Selected user: " + selectedUser.toString());
        System.out.println("1. Change balance");
        System.out.println("2. Delete user");
        System.out.println("0. Back");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.print("Amount: $");
                double amount = scanner.nextDouble();
                adminService.changeBalance((Customer) selectedUser, amount);
            }
            case 2 -> {
                System.out.println("User: " + selectedUser.getUsername() + " deleted!");
                adminService.deleteUser(selectedUser);
            }
            case 0 -> {
                return;
            }

            default -> {
                return;
            }
        }
        ;

        scanner.nextLine();
    }
}
