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

public class AddTransactionController {
    Helper helper = new Helper();
    Librarian librarian = new Librarian();
    @FXML
    private TextField patronIdField;
    @FXML
    private TextField itemIdField;
    @FXML
    private TextField transactionTypeField;
    @FXML
    private DatePicker transactionDateField;
    @FXML
    private DatePicker dueDateField;
    @FXML
    private Button submitTransactionButton;

    @FXML

    private void handleSubmitTransaction() {
        try {
            Helper helper = new Helper();

            // Validate inputs
            if (patronIdField.getText().isEmpty() || itemIdField.getText().isEmpty() ||
                    transactionTypeField.getText().isEmpty() || transactionDateField.getValue() == null || dueDateField.getValue() == null) {
                helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "Please fill in all fields.");
                return;
            }

            // Retrieve input values
            String patronId = patronIdField.getText();
            String itemId = itemIdField.getText();
            String transactionType = transactionTypeField.getText();

            // Get LocalDate values from DatePicker
            LocalDate transactionDate = transactionDateField.getValue();
            LocalDate dueDate = dueDateField.getValue();

            boolean isUpdated = librarian.makeTransaction(patronId, itemId, transactionType, transactionDate, dueDate);
            if (isUpdated) {
                helper.showAlert(Alert.AlertType.INFORMATION, "Transaction Added", "Transaction added successfully!");
            } else {
                helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "An error occurred while adding the transaction.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            helper.showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while interacting with the database.");
        } catch (Exception e) {
            e.printStackTrace();
            helper.showAlert(Alert.AlertType.ERROR, "Unexpected Error", "An unexpected error occurred.");
        }
    }
}

