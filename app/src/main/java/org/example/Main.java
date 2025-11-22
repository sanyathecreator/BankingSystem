package org.example;

import java.util.Scanner;

import org.example.model.Admin;
import org.example.model.Customer;
import org.example.model.User;
import org.example.repository.TransactionRepository;
import org.example.repository.UserRepository;
import org.example.service.AdminService;
import org.example.service.AuthenticationService;
import org.example.service.CustomerService;
import org.example.service.TransactionService;
import org.example.ui.AdminMenu;
import org.example.ui.CustomerMenu;
import org.example.util.DatabaseManager;
import org.example.ui.AuthenticationMenu;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);
    public static final DatabaseManager databaseManager = new DatabaseManager();
    public static final UserRepository userRepository = new UserRepository(databaseManager);
    public static final TransactionRepository transactionRepository = new TransactionRepository(databaseManager);
    public static final AuthenticationService authService = new AuthenticationService(userRepository);
    public static final CustomerService customerService = new CustomerService(userRepository, transactionRepository);
    public static final AdminService adminService = new AdminService(userRepository, transactionRepository);
    public static final TransactionService transactionService = new TransactionService(transactionRepository,
            userRepository);

    public static void main(String[] args) {
        User user = AuthenticationMenu.welcomeMenu(scanner, authService);
        if (user instanceof Customer)
            CustomerMenu.mainMenu((Customer) user, scanner, customerService, transactionService);
        else if (user instanceof Admin)
            AdminMenu.mainMenu((Admin) user, scanner, adminService);

        databaseManager.closeConnection();
        scanner.close();
    }

}
