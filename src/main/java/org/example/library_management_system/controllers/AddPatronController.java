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

public class AddPatronController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField contactInfoField;

    @FXML
    private Button submitButton;

    Helper helper = new Helper();

    @FXML
    private void handleSubmit() {
        Validation validation = new Validation();
        Librarian librarian = new Librarian();
        Patron patron = new Patron();
        patron.setFirstName(firstNameField.getText());
        patron.setLastName(lastNameField.getText());
        patron.setEmail(emailField.getText());
        patron.setContact(contactInfoField.getText());

        if (patron.getFirstName().isEmpty()
            || patron.getLastName().isEmpty()
            || patron.getEmail().isEmpty()
            || patron.getContact().isEmpty()
        ) {
            helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "Please fill in all fields.");
        } else if (!validation.isValidEmail(patron.getEmail())) {
            helper.showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
        } else {
            boolean isPatronAdded = librarian.addPatron(patron);
            if (isPatronAdded) {
                helper.showAlert(Alert.AlertType.INFORMATION, "Success", "Patron has been added successfully!");
                helper.clearFields(firstNameField, lastNameField, emailField, contactInfoField);
            } else {
                    helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "An error occurred. Please try again.");
            }
        }
    }

}
