package org.example.library_management_system.entities;

import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.utils.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Book extends LibraryItem {
    private String isbn;
    private String publicationYear;
    private final Helper helper = new Helper();


    // Constructor
    public Book(){

    }

    public Book(int itemId, String title, String author, String isbn, String publicationYear) {
        super(itemId, title, author);
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    // Getters and Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    // Polymorphic Method
    @Override
    public void displayInfo() {
        System.out.println("Book ID: " + getItemId() +
                "\nTitle: " + getTitle() +
                "\nAuthor: " + getAuthor() +
                "\nGenre: " + isbn +
                "\nPublication Year: " + publicationYear);
    }

    public boolean saveBookToDatabase(String title, String author, String isbn, String itemType, String publicationYear) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            // Modify this query to request generated keys
            String query = "INSERT INTO LibraryItem (title, author, itemType) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = helper.performQuery(query, true, title, author, itemType);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                // Get the generated keys from the first insert
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int itemId = generatedKeys.getInt(1); // Retrieve the generated itemId

                    // Now insert into the book table with the generated itemId
                    String bookQuery = "INSERT INTO book (isbn, publicationYear, bookId) VALUES (?, ?, ?)";
                    PreparedStatement bookPreStatement = helper.performQuery(bookQuery, false, isbn, publicationYear);
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

}
