package org.example.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.example.model.Admin;
import org.example.model.Customer;
import org.example.model.User;
import org.example.util.DatabaseManager;

public class UserRepository {
    private Map<String, User> users;
    private Connection connection;

    public UserRepository(DatabaseManager databaseManager) {
        connection = databaseManager.getConnection();
        users = new HashMap<>();
        loadUsers();
        // Create default admin if no admin exists
        if (!userExists("admin")) {
            createDefaultAdmin();
        }
    }

    public void deleteUser(User user) {
        // Delete user from in-memory cache
        users.remove(user.getUsername());

        String sql = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    public Map<String, User> getAllUsers() {
        return new HashMap<>(users);
    }

    public void saveUser(User user) {
        // Update in-memory cache
        users.put(user.getUsername(), user);

        String sql = "INSERT OR REPLACE INTO users (username, password, balance, is_admin) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());

            if (user instanceof Customer) {
                pstmt.setDouble(3, ((Customer) user).getBalance());
            } else {
                pstmt.setDouble(3, 0.0);
            }

            pstmt.setBoolean(4, user instanceof Admin);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving user: " + e.getMessage());
        }
    }

    private void loadUsers() {
        String sql = "SELECT * FROM users";

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                double balance = rs.getDouble("balance");
                boolean isAdmin = rs.getBoolean("is_admin");
                if (isAdmin) {
                    users.put(username, new Admin(username, password));
                } else {
                    users.put(username, new Customer(username, password, balance));
                }
            }
        } catch (SQLException e) {
            System.err.println("Cannot load users: " + e.getMessage());
        }
    }

    private void createDefaultAdmin() {
        Admin admin = new Admin("admin", "admin");
        users.put("admin", admin);
        saveUser(admin);
    }
}
