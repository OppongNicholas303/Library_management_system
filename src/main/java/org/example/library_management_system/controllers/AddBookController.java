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

public class AddBookController {
    public TextField itemTypeField;

    public TextField publishedDate;

    Helper helper = new Helper();

    Librarian librarian = new Librarian();

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

        Book book = new Book();

        book.setTitle(titleField.getText());
        book.setItemType(itemTypeField.getText());
        book.setIsbn(isbnField.getText());
        book.setPublicationYear(publishedDate.getText());
        book.setIsbn(isbnField.getText());
        book.setAuthor(authorField.getText());

        if (book.getTitle().isEmpty() || book.getAuthor().isEmpty() || book.getIsbn().isEmpty()) {
            helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "Please fill in all fields.");
        } else {
            boolean isUploaded = librarian.addItemToDatabase(book);
            if (isUploaded) {
                helper.showAlert(Alert.AlertType.INFORMATION, "Success", "Book has been added successfully!");
                helper.clearFields(titleField, authorField, isbnField, itemTypeField, publishedDate);
            } else {
                helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "An error occurred. Please try again.");
            }
        }
    }

}
