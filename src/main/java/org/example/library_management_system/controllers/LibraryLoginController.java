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

public class LibraryLoginController {
    Helper helper = new Helper();
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    // Handle the login action
    @FXML
    private void handleLogin(ActionEvent event) {
        String email = usernameField.getText();
        String password = passwordField.getText();

        if(email.isEmpty() || password.isEmpty()){
            helper.showAlert(AlertType.ERROR, "Registration Failed", "Please fill in all fields.");
        } else {
            try {

                String query = "SELECT * FROM librarian WHERE email = ? ";

                PreparedStatement preparedStatement = helper.performQuery(query, false, email);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    String emailGet = resultSet.getString("email");

                    boolean isValidPassword = helper.comparePassword(password, resultSet.getString("password"));

                    if (isValidPassword) {
                        helper.navigateToScene("/org/example/library_management_system/librarianDashboard.fxml", event);
                    }else {
                        helper.showAlert(AlertType.INFORMATION, "Invalid Credentials", "Email or password is wrong");
                    };

                }else {
                    helper.showAlert(AlertType.INFORMATION, "Invalid Credentials", "Email or password is wrong");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
