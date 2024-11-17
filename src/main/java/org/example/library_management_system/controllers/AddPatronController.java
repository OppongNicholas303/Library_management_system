package org.example.library_management_system.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.utils.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddPatronController {
    Helper helper = new Helper();

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

    @FXML
    private void handleSubmit() {
        System.out.println("Clicked");

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String contactInfo = contactInfoField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || contactInfo.isEmpty()) {
            helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "Please fill in all fields.");
        } else if (!isValidEmail(email)) {
            helper.showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
        } else {
            boolean isPatronAdded = savePatronToDatabase(firstName, lastName, email, contactInfo);
            if (isPatronAdded) {
                helper.showAlert(Alert.AlertType.INFORMATION, "Success", "Patron has been added successfully!");
                clearFields();
            } else {
                helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "An error occurred. Please try again.");
            }
        }
    }

    private boolean savePatronToDatabase(String firstName, String lastName, String email, String contactInfo) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO patron (first_name, last_name, email, contact_info) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, contactInfo);

            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidEmail(String email) {
        // Simple email validation
        return email != null && email.contains("@") && email.contains(".");
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        contactInfoField.clear();
    }
}
