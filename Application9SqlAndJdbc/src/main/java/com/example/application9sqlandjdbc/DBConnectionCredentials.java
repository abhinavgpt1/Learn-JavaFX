package com.example.application9sqlandjdbc;

public abstract class DBConnectionCredentials {
    private String connection;
    private String username;
    private String password;

    public DBConnectionCredentials(String connection, String username, String password) {
        this.connection = connection;
        this.username = username;
        this.password = password;
    }

    public String getConnection() {
        return connection;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
