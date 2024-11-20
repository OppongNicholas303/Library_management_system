package org.example.library_management_system.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.services.Librarian;
import org.example.library_management_system.utils.Helper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Controller class for managing the addition of transactions in the library system.
 * Handles user input for transaction details and communicates with the database.
 */
public class AddTransactionController {

    Librarian librarian = new Librarian();  // Librarian class to manage transaction actions

    @FXML
    private TextField patronIdField;  // Input for the patron ID

    @FXML
    private TextField itemIdField;  // Input for the item (book/magazine) ID

    @FXML
    private TextField transactionTypeField;  // Input for the transaction type (borrow/return)

    @FXML
    private DatePicker transactionDateField;  // DatePicker for the transaction date

    @FXML
    private DatePicker dueDateField;  // DatePicker for the due date

    @FXML
    private Button submitTransactionButton;  // Button to submit the transaction

    Helper helper = new Helper();  // Helper class for utility functions

    /**
     * Handles the submission of transaction details. Validates input and adds the transaction to the database.
     */
    @FXML
    private void handleSubmitTransaction() {
        try {
            // Validate the form fields
            if (patronIdField.getText().isEmpty() || itemIdField.getText().isEmpty() ||
                transactionTypeField.getText().isEmpty() || transactionDateField.getValue() == null ||
                dueDateField.getValue() == null) {
                helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "Please fill in all fields.");
                return;
            }

            // Retrieve the input values
            String patronId = patronIdField.getText();
            String itemId = itemIdField.getText();
            String transactionType = transactionTypeField.getText();
            LocalDate transactionDate = transactionDateField.getValue();
            LocalDate dueDate = dueDateField.getValue();

            // Attempt to make the transaction
            boolean isUpdated = librarian.makeTransaction(patronId, itemId, transactionType, transactionDate, dueDate);

            // Show the appropriate message based on success or failure
            if (isUpdated) {
                helper.showAlert(Alert.AlertType.INFORMATION, "Transaction Added", "Transaction added successfully!");
            } else {
                helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "An error occurred while adding the transaction.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            helper.showAlert(Alert.AlertType.ERROR, "Unexpected Error", "An unexpected error occurred.");
        }
    }
}
