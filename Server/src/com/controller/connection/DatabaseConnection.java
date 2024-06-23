package com.controller.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private DatabaseConnection() {
    }

    public void connectToDatabase() {
        try {
            String server = "localhost";
            String port = "3306";
            String database = "jun_chat";
            String userName = "root";
            String password = "123456";
            String url = "jdbc:mysql://" + server + ":" + port + "/" + database;
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            System.err.println("Connection failed!");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
