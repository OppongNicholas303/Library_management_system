package org.example.library_management_system.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.entities.Book;
import org.example.library_management_system.services.Librarian;
import org.example.library_management_system.utils.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Controller class for managing the addition of books to the library system.
 * Handles user input for book details and communicates with the database.
 */
public class AddBookController {

    @FXML
    public TextField itemTypeField;  // Input for the item type (e.g., book, magazine)

    @FXML
    public TextField publishedDate;  // Input for the publication date of the book

    @FXML
    private TextField titleField;  // Input for the book title

    @FXML
    private TextField authorField;  // Input for the book author

    @FXML
    private TextField isbnField;  // Input for the book ISBN

    @FXML
    private Button submitButton;  // Button to submit the form

    Helper helper = new Helper();  // Helper class for utility functions
    Librarian librarian = new Librarian();  // Librarian class to manage database actions

    /**
     * Handles the submission of book details. Validates input and adds the book to the database.
     */
    @FXML
    private void handleSubmit() {

        // Create a new Book object and set its properties from the form
        Book book = new Book();
        book.setTitle(titleField.getText());
        book.setItemType(itemTypeField.getText());
        book.setIsbn(isbnField.getText());
        book.setPublicationYear(publishedDate.getText());
        book.setIsbn(isbnField.getText());
        book.setAuthor(authorField.getText());

        // Validate the form fields
        if (book.getTitle().isEmpty() || book.getAuthor().isEmpty() || book.getIsbn().isEmpty()) {
            helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "Please fill in all fields.");
        } else {
            // Attempt to add the book to the database
            boolean isUploaded = librarian.addItemToDatabase(book);

            // Show appropriate message based on success or failure
            if (isUploaded) {
                helper.showAlert(Alert.AlertType.INFORMATION, "Success", "Book has been added successfully!");
                helper.clearFields(titleField, authorField, isbnField, itemTypeField, publishedDate);
            } else {
                helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "An error occurred. Please try again.");
            }
        }
    }

}
