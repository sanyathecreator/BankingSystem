package org.example.model;

public class Admin extends User {

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin: " + username;
    }
}
