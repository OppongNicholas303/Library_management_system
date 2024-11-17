package org.example.library_management_system.entities;

import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.utils.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Librarian extends Patron {
    private String password;
    private final Helper helper;
    private Book book;
    private Magazine magazine;

    // Constructor
    public Librarian(){
        this.helper = new Helper();
    }
    public Librarian(int patronId, String firstName, String lastName, String email, String contact, String password, Helper helper) {
        super(patronId, firstName, lastName, email, contact);
        this.password = password;
        this.helper = helper;
    }
    // setters and getters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Additional methods specific to librarians
    public void manageLibrary() {
        System.out.println("Managing the library...");
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


    public int register() throws SQLException {
        String hashedPassword = helper.hashPassword(this.password);
        String query = "INSERT INTO librarian (firstName, lastName, contact,  password, email) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = helper.performQuery(query, false, this.getFirstName(), this.getLastName(), this.getContact(), hashedPassword, this.getEmail());
        return  preparedStatement.executeUpdate();
    }

    public void login(){

    }


}

