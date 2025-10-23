package org.example.repository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.example.model.User;

public class UserRepository {
    private static final String DATA_FILE = "users.dat";
    private Map<String, User> users;

    public UserRepository() {
        users = new HashMap<>();
        loadUsers();
    }

    public void saveUser(User user) {
        users.put(user.getUsername(), user);
        saveUsers();
    }

    @SuppressWarnings("unchecked")
    private void loadUsers() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            users = (Map<String, User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Cannot load users. Error: " + e.getMessage());
            users = new HashMap<>();
        }
    }

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println("Cannot save users. Error" + e.getMessage());
        }
    }
}
