package org.example.repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.example.model.Transaction;
import org.example.util.DatabaseManager;

public class TransactionRepository {
    private static final String LOGS_FILE = "logs.txt";
    private List<Transaction> transactions;
    private Connection connection;

    public TransactionRepository(DatabaseManager databaseManager) {
        transactions = new ArrayList<>();
        connection = databaseManager.getConnection();
        loadTransaction();
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    public void saveTransaction(Transaction transaction) {
        transactions.add(transaction);
        saveTransactionToDb(transaction);
    }

    public void logTransactions() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOGS_FILE))) {
            for (Transaction transaction : transactions) {
                writer.write(transaction.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Cannot write transactions to log file. Error: " + e.getMessage());
        }
    }

    private void saveTransactionToDb(Transaction transaction) {
        String sql = "INSERT INTO transactions (sender_username, receiver_username, amount, timestamp) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, transaction.getSenderUsername());
            pstmt.setString(2, transaction.getReceiverUsername());
            pstmt.setDouble(3, transaction.getAmount());
            pstmt.setString(4, transaction.getTimestamp().toString());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving transaction to DB: " + e.getMessage());
        }
    }

    private void loadTransaction() {
        String sql = "SELECT * FROM transactions";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LocalDateTime timestamp = LocalDateTime.parse(rs.getString("timestamp"));
                String senderUsername = rs.getString("sender_username");
                String receiverUsername = rs.getString("receiver_username");
                double amount = rs.getDouble("amount");
                transactions.add(new Transaction(timestamp, senderUsername, receiverUsername, amount));
            }
        } catch (SQLException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
        }
    }
}
