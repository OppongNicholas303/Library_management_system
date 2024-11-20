package org.example.library_management_system.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing the connection to the MySQL database.
 * This class implements a Singleton pattern to ensure that only one
 * connection to the database is created during the lifetime of the application.
 */
public class DatabaseConnection {
    private static Connection connection;  // Static connection object

    // Private constructor to prevent instantiation of this class
    private DatabaseConnection() {}

    /**
     * Returns a single database connection instance. If the connection does not
     * exist, a new connection is created.
     *
     * @return The active database connection.
     * @throws RuntimeException if the database connection cannot be established.
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Database connection URL and credentials
                String url = "jdbc:mysql://localhost:3306/library_management_system?useSSL=false&serverTimezone=UTC";
                String user = "root";
                String password = "NicTech23";

                // Establishing the database connection
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to establish database connection", e);
            }
        }
        return connection;
    }

    /**
     * Closes the database connection if it is currently open.
     * This method can be called to explicitly close the connection when no longer needed.
     *
     * @throws SQLException if an error occurs while closing the connection.
     */
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
