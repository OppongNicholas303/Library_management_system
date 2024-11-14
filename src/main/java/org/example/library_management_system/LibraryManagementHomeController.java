package org.example.library_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LibraryManagementHomeController {

    @FXML
    private void handleLogin(ActionEvent event) {
        // Load the Login page FXML
        try {
            // Create a new FXMLLoader instance and load the login.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            StackPane loginPage = loader.load();  // Load the login page

            // Get the current stage (from the event or elsewhere)
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the login page and set it as the stage
            Scene loginScene = new Scene(loginPage);
            stage.setScene(loginScene);  // Set the new scene
            stage.setMaximized(true);
            stage.show();  // Display the stage
        } catch (IOException e) {
            e.printStackTrace();  // Handle error if login.fxml is not found
        }
    }

    @FXML
    public void handleSignup(ActionEvent event) {
        // Load the SignUp page FXML
        try {
            // Create a new FXMLLoader instance and load the signup.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
            StackPane signupPage = loader.load();  // Load the sign-up page

            // Get the current stage (from the event or elsewhere)
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the sign-up page and set it as the stage
            Scene signupScene = new Scene(signupPage);
            stage.setScene(signupScene);  // Set the new scene
            stage.setMaximized(true);
            stage.show();  // Display the stage
        } catch (IOException e) {
            e.printStackTrace();  // Handle error if signup.fxml is not found
        }
    }
}
