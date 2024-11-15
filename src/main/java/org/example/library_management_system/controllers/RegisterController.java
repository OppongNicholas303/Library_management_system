package org.example.library_management_system.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.library_management_system.dbconnection.DatabaseConnection;
import org.example.library_management_system.utils.Helper;
import org.example.library_management_system.utils.Validation;

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
    Helper helper = new Helper();
    @FXML
    private void handleRegister() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String contact = contactField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        Validation validation = new Validation();

        // Basic validation and feedback (replace with your actual registration logic)
        if (firstName.isEmpty() || lastName.isEmpty() || contact.isEmpty() || email.isEmpty() || password.isEmpty()) {
            helper.showAlert(AlertType.ERROR, "Registration Failed", "Please fill in all fields.");
        } else if(!validation.isValidEmail(email) || ! validation.isValidContact(contact)){
            helper.showAlert(AlertType.ERROR, "Mismatch input", "Check your inputs");
        }
        else {
            try {
                Connection connection = DatabaseConnection.getConnection();
                String query = "INSERT INTO librarian (firstName, lastName, contactInfo,  password, email, employeeId) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                String hashedPassword = helper.hashPassword(password);

                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, contact);
                preparedStatement.setString(4, hashedPassword);
                preparedStatement.setString(5, email);
                preparedStatement.setString(6, "employeeID");

                System.out.println("Click");

                int result = preparedStatement.executeUpdate();
                System.out.println(result);
                if(result > 0){
                    helper.showAlert(AlertType.INFORMATION, "Registration Successful", "Welcome, " + firstName + " " + lastName + "!");
                }else {
                    System.out.println("fail");
                }
            }catch (Exception e){
                System.out.println(e);
            }


        }
    }


}
