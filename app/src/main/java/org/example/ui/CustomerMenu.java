package org.example.ui;

import java.util.Scanner;

import org.example.service.CustomerService;
import org.example.model.Customer;

public class CustomerMenu extends BaseMenu {

    public static void mainMenu(Customer customer, Scanner scanner, CustomerService bankingService) {
        clearConsole();
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
                    clearConsole();
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
}
