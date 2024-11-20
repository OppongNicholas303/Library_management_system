package org.example.library_management_system.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.entities.Patron;
import org.example.library_management_system.services.Librarian;
import org.example.library_management_system.utils.Helper;
import org.example.library_management_system.utils.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Controller class for managing the addition of patrons to the library system.
 * Handles user input for patron details and communicates with the database.
 */
public class AddPatronController {

    @FXML
    private TextField firstNameField;  // Input for the patron's first name

    @FXML
    private TextField lastNameField;  // Input for the patron's last name

    @FXML
    private TextField emailField;  // Input for the patron's email address

    @FXML
    private TextField contactInfoField;  // Input for the patron's contact information

    @FXML
    private Button submitButton;  // Button to submit the form

    Helper helper = new Helper();  // Helper class for utility functions

    /**
     * Handles the submission of patron details. Validates input and adds the patron to the database.
     */
    @FXML
    private void handleSubmit() {
        Validation validation = new Validation();  // Validator for email format
        Librarian librarian = new Librarian();  // Librarian class to manage database actions
        Patron patron = new Patron();  // Patron object to hold form data

        // Set the patron's properties from the form fields
        patron.setFirstName(firstNameField.getText());
        patron.setLastName(lastNameField.getText());
        patron.setEmail(emailField.getText());
        patron.setContact(contactInfoField.getText());

        // Validate the form fields
        if (patron.getFirstName().isEmpty() || patron.getLastName().isEmpty() ||
            patron.getEmail().isEmpty() || patron.getContact().isEmpty()) {
            helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "Please fill in all fields.");
        } else if (!validation.isValidEmail(patron.getEmail())) {
            helper.showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
        } else {
            // Attempt to add the patron to the database
            boolean isPatronAdded = librarian.addPatron(patron);

            // Show appropriate message based on success or failure
            if (isPatronAdded) {
                helper.showAlert(Alert.AlertType.INFORMATION, "Success", "Patron has been added successfully!");
                helper.clearFields(firstNameField, lastNameField, emailField, contactInfoField);
            } else {
                helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "An error occurred. Please try again.");
            }
        }
    }
}
