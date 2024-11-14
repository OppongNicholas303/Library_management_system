package org.example.library_management_system;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LibraryLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    // Handle the login action
    @FXML
    private void handleLogin() {
        String email = usernameField.getText();
        String password = passwordField.getText();




        // Basic check (replace with your actual authentication logic)
        if (username.equals("admin") && password.equals("password")) {
            showAlert(AlertType.INFORMATION, "Login Successful", "Welcome to the Library Management System!");
        } else {
            showAlert(AlertType.ERROR, "Login Failed", "Incorrect username or password.");
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
