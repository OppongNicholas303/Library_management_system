package org.example.library_management_system;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.library_management_system.dbconnection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField contactField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    // Handle the register action
    @FXML
    private void handleRegister() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String contact = contactField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        // Basic validation and feedback (replace with your actual registration logic)
        if (firstName.isEmpty() || lastName.isEmpty() || contact.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Registration Failed", "Please fill in all fields.");
        } else {
            try {
                Connection connection = DatabaseConnection.getConnection();
                String query = "INSERT INTO librarian (firstName, lastName, contactInfo,  password, email, employeeId) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, contact);
                preparedStatement.setString(4, password);
                preparedStatement.setString(5, email);
                preparedStatement.setString(6, "employeeID");

                System.out.println("Click");

                int result = preparedStatement.executeUpdate();
                System.out.println(result);
                if(result > 0){
                    showAlert(AlertType.INFORMATION, "Registration Successful", "Welcome, " + firstName + " " + lastName + "!");
                }else {
                    System.out.println("fail");
                }
            }catch (Exception e){
                System.out.println(e);
            }


        }
    }

    // Utility method to show alert dialogs
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
