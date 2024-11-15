package org.example.library_management_system.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.example.library_management_system.dbconnection.DatabaseConnection;
import org.example.library_management_system.utils.Helper;

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
    private void handleLogin() {
        String email = usernameField.getText();
        String password = passwordField.getText();

        if(email.isEmpty() || password.isEmpty()){
            helper.showAlert(AlertType.ERROR, "Registration Failed", "Please fill in all fields.");
        } else {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM librarian WHERE email = ? ";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, email);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    String emailGet = resultSet.getString("email");
                    System.out.println(emailGet);

                    boolean isValidPassword = helper.comparePassword(password, resultSet.getString("password"));

                    if (isValidPassword) helper.showAlert(AlertType.INFORMATION, "Login", "Success.");;

                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        // Basic check (replace with your actual authentication logic)
//        if (email.equals("admin") && password.equals("password")) {
//            showAlert(AlertType.INFORMATION, "Login Successful", "Welcome to the Library Management System!");
//        } else {
//            showAlert(AlertType.ERROR, "Login Failed", "Incorrect username or password.");
//        }
    }

}
