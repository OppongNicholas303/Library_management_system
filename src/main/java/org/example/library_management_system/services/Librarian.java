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

/**
 * The Librarian class provides various services to interact with the library's
 * database, such as adding items (books, magazines), managing patrons, making
 * transactions, and processing reservations and returns.
 */
public class Librarian {
    public  Helper helper;  // Helper utility class for showing alerts and performing queries

    /**
     * Constructs a Librarian object.
     * Initializes the Helper instance.
     */
    public Librarian() {
        helper = new Helper();
    }

    /**
     * Adds a book to the library database.
     *
     * @param book The book object to be added.
     * @return true if the book was successfully added, false otherwise.
     */
    public boolean addItemToDatabase(Book book) {
        try {
            // Insert LibraryItem record
            String query = "INSERT INTO LibraryItem (title, author, itemType) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = helper.performQuery(
                    query, true,
                    book.getTitle(),
                    book.getAuthor(),
                    book.getItemType()
            );
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                // Retrieve the generated itemId
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int itemId = generatedKeys.getInt(1);  // Retrieve the itemId

                    // Insert Book-specific information
                    String bookQuery = "INSERT INTO book (isbn, publicationYear, bookId) VALUES (?, ?, ?)";
                    PreparedStatement bookPreStatement = helper.performQuery(
                            bookQuery, false,
                            book.getIsbn(),
                            book.getPublicationYear()
                    );

                    bookPreStatement.setInt(3, itemId);

                    int bookResult = bookPreStatement.executeUpdate();
                    return bookResult > 0;
                }
            }
            return false; // If no rows were affected
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds a magazine to the library database.
     *
     * @param magazine The magazine object to be added.
     * @return true if the magazine was successfully added, false otherwise.
     */
    public boolean addItemToDatabase(Magazine magazine) {
        try {
            // Insert LibraryItem record
            String query = "INSERT INTO LibraryItem (title, author, itemType) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = helper.performQuery(
                    query, true,
                    magazine.getTitle(),
                    magazine.getAuthor(),
                    magazine.getItemType()
            );

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                // Retrieve the generated itemId
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int itemId = generatedKeys.getInt(1);  // Retrieve the itemId

                    // Insert Magazine-specific information
                    String magazineQuery = "INSERT INTO magazine (issueNumber, magazineId) VALUES (?, ?)";
                    PreparedStatement magazinePreStatement = helper.performQuery(
                            magazineQuery, false,
                            magazine.getIssueNumber()
                    );
                    magazinePreStatement.setInt(2, itemId);

                    int magazineResult = magazinePreStatement.executeUpdate();
                    return magazineResult > 0;
                }
            }
            return false; // If no rows were affected
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds a patron to the library database.
     *
     * @param patron The patron object to be added.
     * @return true if the patron was successfully added, false otherwise.
     */
    public boolean addPatron(Patron patron) {
        try {
            // Insert patron record
            String query = "INSERT INTO patron (firstName, lastName, email, contact) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = helper.performQuery(
                    query, false,
                    patron.getFirstName(),
                    patron.getLastName(),
                    patron.getEmail(),
                    patron.getContact()
            );

            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Makes a transaction for a patron (e.g., borrowing an item).
     *
     * @param patronId The ID of the patron performing the transaction.
     * @param itemId The ID of the item being transacted.
     * @param transactionType The type of transaction (e.g., 'Borrow').
     * @param transactionDate The date of the transaction.
     * @param dueDate The due date for returning the item.
     * @return true if the transaction was successful, false otherwise.
     * @throws SQLException if an error occurs during SQL execution.
     */
    public boolean makeTransaction(
            String patronId,
            String itemId,
            String transactionType,
            LocalDate transactionDate,
            LocalDate dueDate
    ) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();

        // Check if the patron exists in the database
        String patronQuery = "SELECT patronId FROM Patron WHERE patronId = ?";
        PreparedStatement patronPreparedStatement = connection.prepareStatement(patronQuery);
        patronPreparedStatement.setString(1, patronId);
        ResultSet patronResultSet = patronPreparedStatement.executeQuery();

        if (!patronResultSet.next()) {
            helper.showAlert(Alert.AlertType.ERROR, "Transaction Failed", "Patron does not exist.");
            return false;
        }

        // Check if the item is available for transaction
        String itemQuery = "SELECT availability_status FROM LibraryItem WHERE itemId = ?";
        PreparedStatement itemPreparedStatement = connection.prepareStatement(itemQuery);
        itemPreparedStatement.setString(1, itemId);
        ResultSet itemResultSet = itemPreparedStatement.executeQuery();

        if (itemResultSet.next()) {
            boolean isAvailable = itemResultSet.getBoolean("availability_status");
            if (!isAvailable) {
                helper.showAlert(Alert.AlertType.INFORMATION, "Transaction Failed", "Item not available.");
                return false;
            }
        }

        // Insert the transaction record into the Transaction table
        String transactionQuery = "INSERT INTO Transaction (patronId, itemId, transactionType, transactionDate, dueDate, returned) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement transactionPreparedStatement = connection.prepareStatement(transactionQuery);
        transactionPreparedStatement.setString(1, patronId);
        transactionPreparedStatement.setString(2, itemId);
        transactionPreparedStatement.setString(3, transactionType);
        transactionPreparedStatement.setDate(4, Date.valueOf(transactionDate));
        transactionPreparedStatement.setDate(5, Date.valueOf(dueDate));
        transactionPreparedStatement.setBoolean(6, false);

        int result = transactionPreparedStatement.executeUpdate();

        if (result > 0) {
            // Update the item's availability status
            String updateItemQuery = "UPDATE LibraryItem SET availability_status = FALSE WHERE itemId = ?";
            PreparedStatement updateItemPreparedStatement = connection.prepareStatement(updateItemQuery);
            updateItemPreparedStatement.setString(1, itemId);
            result = updateItemPreparedStatement.executeUpdate();

            return result > 0;
        }
        return false;
    }

    /**
     * Makes a reservation for a patron.
     *
     * @param reservation The reservation object containing the patron ID, item ID, and reservation date.
     * @return true if the reservation was successful, false otherwise.
     * @throws SQLException if an error occurs during SQL execution.
     */
    public boolean makeReservation(Reservation reservation) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "INSERT INTO reservation (patronId, itemId, reservationDate) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, reservation.getPatronId());
        preparedStatement.setInt(2, reservation.getItemID());
        preparedStatement.setDate(3, Date.valueOf(reservation.getReservationDate()));

        int result = preparedStatement.executeUpdate();
        return result > 0;
    }

    /**
     * Processes the return of an item by updating the transaction and item availability status.
     *
     * @param itemId The ID of the item being returned.
     * @param patronId The ID of the patron returning the item.
     * @return true if the item was successfully returned, false otherwise.
     * @throws SQLException if an error occurs during SQL execution.
     */
    public boolean returnItem(String itemId, String patronId) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();

        String query = """
        UPDATE Transaction t
        JOIN LibraryItem li ON t.itemId = li.itemId
        SET t.transactionType = 'Return',
            li.availability_status = TRUE
        WHERE t.patronId = ? AND t.itemId = ?;
    """;

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, Integer.parseInt(patronId));
        preparedStatement.setInt(2, Integer.parseInt(itemId));

        int rowsUpdated = preparedStatement.executeUpdate();

        return rowsUpdated > 0;
    }
}
