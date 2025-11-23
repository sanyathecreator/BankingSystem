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
import org.example.util.PasswordHasher;

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

    public boolean userExistsInDb(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Error finding user in DB: " + e.getMessage());
        }

        return false;
    }

    public Map<String, User> getAllUsers() {
        return new HashMap<>(users);
    }

    public void saveUser(User user) {
        // Update in-memory cache
        users.put(user.getUsername(), user);
        if (userExistsInDb(user.getUsername())) {
            updateUserInDb(user);
        } else {
            insertUserInDb(user);
        }
    }

    private void insertUserInDb(User user) {
        String sql = "INSERT INTO users (username, password, balance, is_admin) VALUES(?, ?, ?, ?)";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, user.getUsername());
            pstm.setString(2, user.getPassword());
            if (user instanceof Customer) {
                pstm.setDouble(3, ((Customer) user).getBalance());
            } else {
                pstm.setDouble(3, 0.0);
            }

            pstm.setBoolean(4, user instanceof Admin);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting user: " + e.getMessage());
        }
    }

    private void updateUserInDb(User user) {
        String sql = "UPDATE users SET password = ?, balance = ?, is_admin = ? WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getPassword());
            if (user instanceof Customer) {
                pstmt.setDouble(2, ((Customer) user).getBalance());
            } else {
                pstmt.setDouble(2, 0.0);
            }
            pstmt.setBoolean(3, user instanceof Admin);
            pstmt.setString(4, user.getUsername());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
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
        String hashedPassword = PasswordHasher.hashPassword("admin");
        Admin admin = new Admin("admin", hashedPassword);
        users.put("admin", admin);
        saveUser(admin);
    }
}
