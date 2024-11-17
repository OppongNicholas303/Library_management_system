package org.example.library_management_system.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class LibraryDatabaseSetup {
    public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/library_management_system";
        String user = "nicholas";
        String password = "NicTech23";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            // Create LibraryItem table
            String createLibraryItemTable = """
                CREATE TABLE IF NOT EXISTS LibraryItem (
                    itemId INT PRIMARY KEY AUTO_INCREMENT,
                    title VARCHAR(255) NOT NULL,
                    author VARCHAR(255) NOT NULL,
                    availability_status BOOLEAN DEFAULT TRUE,
                    itemType ENUM('Book', 'Magazine') NOT NULL
                );
            """;
            statement.execute(createLibraryItemTable);

            // Create Book table
            String createBookTable = """
                CREATE TABLE IF NOT EXISTS Book (
                    bookId INT PRIMARY KEY,
                    isbn VARCHAR(100),
                    publicationYear INT,
                    FOREIGN KEY (bookId) REFERENCES LibraryItem(itemId)
                        ON DELETE CASCADE ON UPDATE CASCADE
                );
            """;
            statement.execute(createBookTable);

            // Create Magazine table
            String createMagazineTable = """
                CREATE TABLE IF NOT EXISTS Magazine (
                    magazineId INT PRIMARY KEY,
                    issueNumber VARCHAR(50),
                    FOREIGN KEY (magazineId) REFERENCES LibraryItem(itemId)
                        ON DELETE CASCADE ON UPDATE CASCADE
                );
            """;
            statement.execute(createMagazineTable);

            // Create Patron table
            String createPatronTable = """
                CREATE TABLE IF NOT EXISTS Patron (
                    patronId INT PRIMARY KEY AUTO_INCREMENT,
                    firstName VARCHAR(255) NOT NULL,
                    lastName VARCHAR(255) NOT NULL,
                    contact VARCHAR(255) NOT NULL,
                    email VARCHAR(255) UNIQUE NOT NULL
                );
            """;
            statement.execute(createPatronTable);

            // Create Librarian table
            String createLibrarianTable = """
                CREATE TABLE IF NOT EXISTS Librarian (
                    librarianId INT PRIMARY KEY AUTO_INCREMENT,
                    firstName VARCHAR(255) NOT NULL,
                    lastName VARCHAR(255) NOT NULL,
                    contact VARCHAR(255) NOT NULL,
                    email VARCHAR(255) UNIQUE NOT NULL,
                    password VARCHAR(255) NOT NULL
                );
            """;
            statement.execute(createLibrarianTable);

            // Create Transaction table
            String createTransactionTable = """
                CREATE TABLE IF NOT EXISTS Transaction (
                    transactionId INT PRIMARY KEY AUTO_INCREMENT,
                    patronId INT NOT NULL,
                    itemId INT NOT NULL,
                    transactionType ENUM('borrow', 'return') NOT NULL,
                    transactionDate DATE NOT NULL,
                    dueDate DATE NOT NULL,
                    returned BOOLEAN DEFAULT FALSE,
                    FOREIGN KEY (patronId) REFERENCES Patron(patronId)
                        ON DELETE CASCADE ON UPDATE CASCADE,
                    FOREIGN KEY (itemId) REFERENCES LibraryItem(itemId)
                        ON DELETE CASCADE ON UPDATE CASCADE
                );
            """;
            statement.execute(createTransactionTable);

            // Create Reservation table
            String createReservationTable = """
                CREATE TABLE IF NOT EXISTS Reservation (
                    reservationId INT PRIMARY KEY AUTO_INCREMENT,
                    patronId INT NOT NULL,
                    itemId INT NOT NULL,
                    reservationDate DATE NOT NULL,
                    FOREIGN KEY (patronId) REFERENCES Patron(patronId)
                        ON DELETE CASCADE ON UPDATE CASCADE,
                    FOREIGN KEY (itemId) REFERENCES LibraryItem(itemId)
                        ON DELETE CASCADE ON UPDATE CASCADE
                );
            """;
            statement.execute(createReservationTable);

            // Create Fine table
            String createFineTable = """
                CREATE TABLE IF NOT EXISTS Fine (
                    fineId INT PRIMARY KEY AUTO_INCREMENT,
                    transactionId INT NOT NULL,
                    fineAmount DECIMAL(10, 2) NOT NULL,
                    issuedDate DATE NOT NULL,
                    FOREIGN KEY (transactionId) REFERENCES Transaction(transactionId)
                        ON DELETE CASCADE ON UPDATE CASCADE
                );
            """;
            statement.execute(createFineTable);

            System.out.println("Tables created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
