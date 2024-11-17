package org.example.library_management_system.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.entities.Librarian;
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
        Librarian librarian = new Librarian();

        librarian.setFirstName(firstNameField.getText().trim());
        librarian.setLastName(lastNameField.getText().trim());
        librarian.setContact(contactField.getText().trim());
        librarian.setEmail(emailField.getText().trim());
        librarian.setPassword(passwordField.getText().trim());

        Validation validation = new Validation();

        // Basic validation and feedback (replace with your actual registration logic)
        if (librarian.getFirstName().isEmpty() || librarian.getLastName().isEmpty() || librarian.getContact().isEmpty() || librarian.getEmail().isEmpty() || librarian.getPassword().isEmpty()) {
            helper.showAlert(AlertType.ERROR, "Registration Failed", "Please fill in all fields.");
        } else if(!validation.isValidEmail(librarian.getEmail()) || ! validation.isValidContact(librarian.getContact())){
            helper.showAlert(AlertType.ERROR, "Mismatch input", "Check your inputs");
        }
        else {
            try {
                int result = librarian.register();
                if(result > 0){
                    helper.showAlert(AlertType.INFORMATION, "Registration Successful", "Welcome, " + librarian.getFirstName() + " " + librarian.getLastName() + "!");
                }else {
                    System.out.println("fail");
                }
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }


}
