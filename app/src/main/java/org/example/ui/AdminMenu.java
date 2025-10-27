package org.example.ui;

import java.util.List;
import java.util.Scanner;

import org.example.model.Admin;
import org.example.model.User;
import org.example.service.AdminService;

public class AdminMenu extends BaseMenu {

    public static void mainMenu(Admin admin, Scanner scanner, AdminService adminService) {
        clearConsole();
        System.out.println("Welcome, " + admin.getUsername() + "\n");
        while (true) {
            List<User> users = adminService.getAllUsers();
            System.out.println("1. Display all users");
            System.out.println("2. Find user");
            System.out.println("0. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    displayAllUsers(users);
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void displayAllUsers(List<User> users) {
        clearConsole();
        for (int i = 0; i < users.size(); i++) {
            System.out.println(i + ". " + users.get(i).toString());
        }
    }
}
