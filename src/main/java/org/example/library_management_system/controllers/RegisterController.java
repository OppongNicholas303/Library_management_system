package org.example.library_management_system.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.entities.Librarian;
import org.example.library_management_system.utils.Helper;
import org.example.library_management_system.utils.Validation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Controller class for handling the librarian registration process in the library management system.
 * It manages the user interface for registering a librarian and performs validation on input fields.
 */
public class RegisterController {

    @FXML
    public TextField firstNameField;  // Field for librarian's first name

    @FXML
    public TextField lastNameField;   // Field for librarian's last name

    @FXML
    public TextField contactField;    // Field for librarian's contact information

    @FXML
    public TextField emailField;      // Field for librarian's email address

    @FXML
    public PasswordField passwordField;  // Field for librarian's password

    @FXML
    public Button registerButton;     // Button for submitting the registration form

    public Button backButton;         // Button for navigating back to the homepage

    public Helper helper = new Helper();  // Utility class for helper methods

    // Create a new Librarian object and set its properties from the form fields
    Librarian librarian = new Librarian();

    // Create a validation object
    Validation validation = new Validation();

    /**
     * Handles the registration action when the librarian submits the registration form.
     * Validates the input fields, performs registration, and shows appropriate feedback messages.
     */
    @FXML
    public void handleRegister() {

        librarian.setFirstName(firstNameField.getText().trim());
        librarian.setLastName(lastNameField.getText().trim());
        librarian.setContact(contactField.getText().trim());
        librarian.setEmail(emailField.getText().trim());
        librarian.setPassword(passwordField.getText().trim());

        // Check if any field is empty or if input values are invalid
        if (librarian.getFirstName().isEmpty() || librarian.getLastName().isEmpty() ||
            librarian.getContact().isEmpty() || librarian.getEmail().isEmpty() ||
            librarian.getPassword().isEmpty()) {
            helper.showAlert(AlertType.ERROR, "Registration Failed", "Please fill in all fields.");
        } else if (!validation.isValidEmail(librarian.getEmail()) || !validation.isValidContact(librarian.getContact())) {
            helper.showAlert(AlertType.ERROR, "Mismatch input", "Check your inputs.");
        } else {
            // Attempt to register the librarian in the database
            try {
                int result = librarian.register();
                if (result > 0) {
                    // Show success alert if registration is successful
                    helper.showAlert(
                            AlertType.INFORMATION,
                            "Registration Successful",
                            "Welcome, " + librarian.getFirstName() + " " + librarian.getLastName() + "!"
                    );
                } else {
                    // If registration fails, log the failure
                    System.out.println("Registration failed.");

                }
            } catch (Exception e) {
                // Log any exceptions encountered during registration
                System.out.println(e);
            }
        }
    }

    /**
     * Navigates back to the homepage when the back button is clicked.
     *
     * @param actionEvent the event triggered by the back button click
     */
    public void handleBack(ActionEvent actionEvent) {
        try {
            // Navigate to the homepage scene
            helper.navigateToScene("/org/example/library_management_system/homepage.fxml", actionEvent);
        } catch (IOException e) {
            // Handle any exceptions encountered during navigation
            throw new RuntimeException(e);
        }
    }
}
