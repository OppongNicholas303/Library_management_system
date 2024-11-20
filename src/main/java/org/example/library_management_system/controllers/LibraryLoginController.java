package org.example.library_management_system.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.utils.Helper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Controller class responsible for handling the librarian login process.
 * It validates login credentials, navigates to the librarian dashboard if successful,
 * and handles errors if login fails.
 */
public class LibraryLoginController {

    @FXML
    private Button backButton;  // Button to navigate back to the homepage

    @FXML
    private TextField usernameField;  // TextField for entering username (email)

    @FXML
    private PasswordField passwordField;  // PasswordField for entering password

    @FXML
    private Button loginButton;  // Button to submit login credentials

    private final Helper helper = new Helper();  // Helper object to handle utility tasks like alerts and navigation

    /**
     * Handles the login process. Validates the input fields and checks the credentials
     * against the database. If valid, navigates to the librarian dashboard.
     *
     * @param event The ActionEvent triggered when the login button is clicked.
     */
    @FXML
    private void handleLogin(ActionEvent event) {

        String email = usernameField.getText();
        String password = passwordField.getText();

        // Check if any field is empty
        if(email.isEmpty() || password.isEmpty()){
            helper.showAlert(AlertType.ERROR, "Registration Failed", "Please fill in all fields.");
        } else {
            try {
                // Query to check if the email exists in the database
                String query = "SELECT * FROM librarian WHERE email = ? ";
                PreparedStatement preparedStatement = helper.performQuery(query, false, email);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Validate the email and password
                if(resultSet.next()){
                    String emailGet = resultSet.getString("email");

                    boolean isValidPassword = helper.comparePassword(password, resultSet.getString("password"));
                    if (isValidPassword) {
                        // Navigate to the librarian dashboard if credentials are correct
                        helper.navigateToScene("/org/example/library_management_system/librarianDashboard.fxml", event);
                    } else {
                        helper.showAlert(AlertType.INFORMATION, "Invalid Credentials", "Email or password is wrong");
                    }
                } else {
                    helper.showAlert(AlertType.INFORMATION, "Invalid Credentials", "Email or password is wrong");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Navigates the user back to the homepage.
     *
     * @param actionEvent The ActionEvent triggered when the back button is clicked.
     */
    public void handleBack(ActionEvent actionEvent) {
        try {
            helper.navigateToScene("/org/example/library_management_system/homepage.fxml", actionEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
