package org.example.repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.example.model.Transaction;

public class TransactionRepository {
    private static final String DATA_FILE = "transactions.dat";
    private static final String LOGS_FILE = "logs.txt";
    private List<Transaction> transactions;

    public TransactionRepository() {
        transactions = new ArrayList<>();
        loadTransaction();
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    public void saveTransaction(Transaction transaction) {
        transactions.add(transaction);
        saveTransactions();
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

    @SuppressWarnings("unchecked")
    private void loadTransaction() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Cannot create " + DATA_FILE + " file. Error: " + e.getMessage());
            }
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            transactions = (List<Transaction>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Cannot load transactions. Error: " + e.getMessage());
            transactions = new ArrayList<>();
        }
    }

    private void saveTransactions() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(transactions);
        } catch (IOException e) {
            System.err.println("Cannot save transactions. Error" + e.getMessage());
        }
    }

}
