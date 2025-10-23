package org.example.service;

import org.example.model.User;

public class BankingService {

    public void checkBalance(User user) {
        System.out.println("Your balance: $" + String.format(".2f", user.getBalance()));
    }

    public void transfer(User user, double amount) {
        if (user.getBalance() < amount) {
            System.out.println("You don't have enough money on your balance!");
            return;
        }
        user.setBalance(user.getBalance() - amount);
        System.out.println(
                "Transfered: $" + amount +
                        "\nYour balance: $" + user.getBalance());
    }
}
