package org.example.library_management_system.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.library_management_system.dbconnection.DatabaseConnection;
import org.example.library_management_system.utils.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddBookController {
Helper helper = new Helper();
    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField isbnField;

    @FXML
    private Button submitButton;

    @FXML
    private void handleSubmit() {
        System.out.println("Clic");
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();

        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
            helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "Please fill in all fields.");
        } else {
            boolean isUploaded = saveBookToDatabase(title, author, isbn);
            if (isUploaded) {
                helper.showAlert(Alert.AlertType.INFORMATION, "Success", "Book has been added successfully!");
                clearFields();
            } else {
                helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "An error occurred. Please try again.");
            }
        }
    }

    private boolean saveBookToDatabase(String title, String author, String isbn) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            // Modify this query to request generated keys
            String query = "INSERT INTO item (title, author, availability_status) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS); // Request generated keys
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setBoolean(3, true);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                // Get the generated keys from the first insert
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int itemId = generatedKeys.getInt(1); // Retrieve the generated itemId

                    // Now insert into the book table with the generated itemId
                    String bookQuery = "INSERT INTO book (itemId, isbn) VALUES (?, ?)";
                    PreparedStatement bookPreStatement = connection.prepareStatement(bookQuery);
                    bookPreStatement.setInt(1, itemId);
                    bookPreStatement.setString(2, isbn);

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


    private void clearFields() {
        titleField.clear();
        authorField.clear();
        isbnField.clear();
    }

}
