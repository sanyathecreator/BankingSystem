package org.example.model;

public class Customer extends User {
    private double balance;

    public Customer() {
        this.username = "Unknown";
        this.password = "1234";
        this.balance = 0;
    }

    public Customer(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double amount) {
        balance = amount;
    }

    @Override
    public String toString() {
        return "Username: " + username + " | Balance: $" + balance;
    }
}
