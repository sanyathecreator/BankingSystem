package org.example.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
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

    @Override
    public String toString() {
        String formattedString = String.format("[%s] From: %s To: %s Amount: %.2f",
                timestamp, senderUsername, receiverUsername, amount);
        return formattedString;
    }
}
