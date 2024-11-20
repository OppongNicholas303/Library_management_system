package org.example.library_management_system.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    // Private constructor to prevent instantiation
    private DatabaseConnection() {}

    // Static method to get the database connection
    public static Connection getConnection() {
        if (connection == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/library_management_system?useSSL=false&serverTimezone=UTC";
                String user = "root";
                String password = "NicTech23";
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to establish database connection", e);
            }
        }
        return connection;
    }

    // Close connection if needed (optional)
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
