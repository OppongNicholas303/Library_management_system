package org.example.library_management_system.services;

import javafx.scene.control.Alert;
import org.example.library_management_system.Transactions.Reservation;
import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.entities.Book;
import org.example.library_management_system.entities.Magazine;
import org.example.library_management_system.entities.Patron;
import org.example.library_management_system.utils.Helper;

import java.sql.*;
import java.time.LocalDate;

public class Librarian {
    Helper helper;

    public Librarian() {
        helper = new Helper();
    }

    public boolean addItemToDatabase(Book book) {
        try {
            //Connection connection = DatabaseConnection.getConnection();

            // Modify this query to request generated keys
            String query = "INSERT INTO LibraryItem (title, author, itemType) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = helper.performQuery(query, true, book.getTitle(), book.getAuthor(), book.getItemType());

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                // Get the generated keys from the first insert
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int itemId = generatedKeys.getInt(1); // Retrieve the generated itemId

                    // Now insert into the book table with the generated itemId
                    String bookQuery = "INSERT INTO book (isbn, publicationYear, bookId) VALUES (?, ?, ?)";
                    PreparedStatement bookPreStatement = helper.performQuery(bookQuery, false, book.getTitle(), book.getPublicationYear());
                    bookPreStatement.setInt(3, itemId);

                    int bookResult = bookPreStatement.executeUpdate();
                    return bookResult > 0;  // Return true if the book was successfully inserted
                }
            }
            return false; // If no rows were affected
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if an exception occurs
        }
    }

    public boolean addItemToDatabase(Magazine magazine ) {
        try {
            // Connection connection = DatabaseConnection.getConnection();

            // Modify this query to request generated keys
            String query = "INSERT INTO LibraryItem (title, author, itemType) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = helper.performQuery(query, true, magazine.getTitle(), magazine.getAuthor(), magazine.getItemType());

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                // Get the generated keys from the first insert
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int itemId = generatedKeys.getInt(1); // Retrieve the generated itemId

                    // Now insert into the inheritance table with the generated itemId
                    String bookQuery = "INSERT INTO magazine (issueNumber, magazineId) VALUES ( ?, ?)";
                    PreparedStatement bookPreStatement = helper.performQuery(bookQuery, false, magazine.getIssueNumber());
                    bookPreStatement.setInt(2, itemId);

                    int bookResult = bookPreStatement.executeUpdate();
                    return bookResult > 0;  // Return true if the book was successfully inserted
                }
            }
            return false; // If no rows were affected
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if an exception occurs
        }
    }

    public int register(org.example.library_management_system.entities.Librarian librarian) throws SQLException {
        String hashedPassword = helper.hashPassword(librarian.getPassword());
        String query = "INSERT INTO librarian (firstName, lastName, contact,  password, email) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = helper.performQuery(query, false, librarian.getFirstName(), librarian.getLastName(), librarian.getContact(), hashedPassword, librarian.getEmail());
        return  preparedStatement.executeUpdate();
    }

    public boolean addPatron(Patron patron){
        try {

            String query = "INSERT INTO patron (firstName, lastName, email, contact) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = helper.performQuery(query, false, patron.getFirstName(), patron.getLastName(), patron.getEmail(), patron.getContact());

            int result = preparedStatement.executeUpdate();

            return  result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if an exception occurs
        }
    }


//    public boolean makeTransaction(String patronId, String itemId, String transactionType, LocalDate transactionDate, LocalDate dueDate) throws SQLException {
//        // Establish the database connection
//
//        Connection connection = DatabaseConnection.getConnection();
//
//        String IdQuery  = "SELECT availability_status FROM LibraryItem WHERE itemId = ?";
//
//        PreparedStatement idPreparedStatement = connection.prepareStatement(IdQuery);
//        idPreparedStatement.setString(1, itemId);
//        ResultSet idResultSet = idPreparedStatement.executeQuery();
//        if(idResultSet.next()){
//            boolean isAvailable = idResultSet.getBoolean("availability_status");
//            if(!isAvailable){
//                helper.showAlert(Alert.AlertType.INFORMATION, "Transaction fail", "Item not available");
//                return false;
//            }
//        }
//
//        String query = "INSERT INTO Transaction (patronId, itemId, transactionType, transactionDate, dueDate, returned) VALUES (?, ?, ?, ?, ?, ?)";
//        PreparedStatement preparedStatement = connection.prepareStatement(query);
//
//        // Set parameters for the SQL query
//        preparedStatement.setInt(1, Integer.parseInt(patronId));
//        preparedStatement.setInt(2, Integer.parseInt(itemId));
//        preparedStatement.setString(3, transactionType);
//
//        preparedStatement.setDate(4, Date.valueOf(transactionDate)); // Convert LocalDate to Date
//        preparedStatement.setDate(5, Date.valueOf(dueDate)); // Convert LocalDate to Date
//        preparedStatement.setBoolean(6, false); // Default value for "returned" field
//
//        // Execute the query
//        int result = preparedStatement.executeUpdate();
//
//        if(result > 0){
//            String updateQuery =  "UPDATE LibraryItem SET availability_status = FALSE WHERE itemId = ?";
//            PreparedStatement updatePreparedStatement = connection.prepareStatement(updateQuery);
//
//            updatePreparedStatement.setInt(1, Integer.parseInt(itemId));
//            result = updatePreparedStatement.executeUpdate();
//
//            return result > 0;
//
//        }
//        return false;
//    }

    public boolean makeTransaction(String patronId, String itemId, String transactionType, LocalDate transactionDate, LocalDate dueDate) throws SQLException {
        // Establish the database connection
        Connection connection = DatabaseConnection.getConnection();

        // Check if the patron exists in the Patron table
        String patronQuery = "SELECT patronId FROM Patron WHERE patronId = ?";
        PreparedStatement patronPreparedStatement = connection.prepareStatement(patronQuery);
        patronPreparedStatement.setString(1, patronId);
        ResultSet patronResultSet = patronPreparedStatement.executeQuery();

        // If no patron is found, show an error and return false
        if (!patronResultSet.next()) {
            helper.showAlert(Alert.AlertType.ERROR, "Transaction Failed", "Patron does not exist.");
            return false;
        }

        // Check the availability status of the item
        String itemQuery = "SELECT availability_status FROM LibraryItem WHERE itemId = ?";
        PreparedStatement itemPreparedStatement = connection.prepareStatement(itemQuery);
        itemPreparedStatement.setString(1, itemId);
        ResultSet itemResultSet = itemPreparedStatement.executeQuery();
        if (itemResultSet.next()) {
            boolean isAvailable = itemResultSet.getBoolean("availability_status");
            if (!isAvailable) {
                helper.showAlert(Alert.AlertType.INFORMATION, "Transaction failed", "Item not available.");
                return false;
            }
        }

        // Insert the transaction into the Transaction table
        String transactionQuery = "INSERT INTO Transaction (patronId, itemId, transactionType, transactionDate, dueDate, returned) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement transactionPreparedStatement = connection.prepareStatement(transactionQuery);

        // Set parameters for the transaction
        transactionPreparedStatement.setString(1, patronId);
        transactionPreparedStatement.setString(2, itemId);
        transactionPreparedStatement.setString(3, transactionType);
        transactionPreparedStatement.setDate(4, Date.valueOf(transactionDate));  // Convert LocalDate to Date
        transactionPreparedStatement.setDate(5, Date.valueOf(dueDate));  // Convert LocalDate to Date
        transactionPreparedStatement.setBoolean(6, false);  // Default value for "returned"

        // Execute the transaction insertion
        int result = transactionPreparedStatement.executeUpdate();

        if (result > 0) {
            // If transaction was successfully added, update the availability status of the item
            String updateItemQuery = "UPDATE LibraryItem SET availability_status = FALSE WHERE itemId = ?";
            PreparedStatement updateItemPreparedStatement = connection.prepareStatement(updateItemQuery);
            updateItemPreparedStatement.setString(1, itemId);
            result = updateItemPreparedStatement.executeUpdate();

            return result > 0;  // If the item status was updated successfully
        }
        return false;
    }


    public boolean makeReservation(Reservation reservation) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "INSERT INTO reservation (patronId, itemId, reservationDate  ) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, reservation.getPatronId());
        preparedStatement.setInt(2, reservation.getPatronId());
        preparedStatement.setDate(3, Date.valueOf(reservation.getReservationDate()));

        int result = preparedStatement.executeUpdate();

        return result > 0;
    }

    public  boolean returnItem(String itemId, String patronId) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
//        String query = """
//            UPDATE Transaction t
//            JOIN LibraryItem li ON t.itemId = li.itemId
//            SET t.transactionType = 'Return',
//                li.availability_status = TRUE
//            WHERE t.patronId = ? AND t.itemId = ?;
//        """;
//
//        String query = """
//            UPDATE Transaction t
//            JOIN LibraryItem li ON t.itemId = li.itemId
//            SET t.transactionType = 'Return',
//                li.availability_status = TRUE
//            WHERE t.itemId = ? AND t.patronId = ?;
//        """;

        String queryTransaction = """
            UPDATE Transaction
            SET transactionType = 'Return'
            WHERE itemId = ? AND patronId = ?;
        """;

        String queryLibraryItem = """
            UPDATE LibraryItem
            SET availability_status = TRUE
            WHERE itemId = ?;
        """;

        PreparedStatement preparedStatement = connection.prepareStatement(queryTransaction);
        preparedStatement.setInt(1, Integer.parseInt(itemId));
        preparedStatement.setInt(2, Integer.parseInt(patronId));
        int transactionResult = preparedStatement.executeUpdate();

        if(transactionResult > 0){
            PreparedStatement preparedStatement1 = connection.prepareStatement(queryLibraryItem);
            preparedStatement1.setInt(1, Integer.parseInt(itemId));
            int libraryItemnResult = preparedStatement.executeUpdate();

           return libraryItemnResult > 0;

        }

        return false;
    }
}
