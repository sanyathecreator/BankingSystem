package org.example.model;

import java.time.LocalDateTime;

public class Transaction {
    private LocalDateTime timestamp;
    private String senderUsername;
    private String receiverUsername;
    private double amount;

    public Transaction(String senderUsername, String receiverUsername, double amount) {
        this.timestamp = LocalDateTime.now();
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public double getAmount() {
        return amount;
    }

}
