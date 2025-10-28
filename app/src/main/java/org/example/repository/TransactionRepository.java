package org.example.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import org.example.model.Transaction;

public class TransactionRepository {
    private static final String DATA_FILE = "transactions.dat";
    private Map<String, Transaction> transactions;

    public TransactionRepository() {
        transactions = new HashMap<>();
        loadTransaction();
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
            transactions = (Map<String, Transaction>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Cannot load transactions. Error: " + e.getMessage());
            transactions = new HashMap<>();
        }
    }
}
