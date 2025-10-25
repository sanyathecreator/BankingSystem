package org.example;

import java.util.Scanner;

import org.example.model.Customer;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.AuthenticationService;
import org.example.service.BankingService;
import org.example.ui.CustomerMenu;
import org.example.ui.WelcomeMenu;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);
    public static final UserRepository userRepository = new UserRepository();
    public static final AuthenticationService authService = new AuthenticationService(userRepository);
    public static final BankingService bankingService = new BankingService(userRepository);

    public static void main(String[] args) {
        User user = WelcomeMenu.welcomeMenu(scanner, authService);
        CustomerMenu.mainMenu((Customer) user, scanner, bankingService);
    }

}
