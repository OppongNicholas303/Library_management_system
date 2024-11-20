package org.example.library_management_system.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.library_management_system.utils.Helper;

import java.io.IOException;

public class LibraryManagementHomeController {
    Helper helper = new Helper();


    @FXML
    private void handleLogin(ActionEvent event) {
        // Load the Login page FXML
        try {
            helper.navigateToScene("/org/example/library_management_system/login.fxml", event);
        } catch (IOException e) {
            e.printStackTrace();  // Handle error if login.fxml is not found
        }
    }

    @FXML
    public void handleSignup(ActionEvent event) {
        // Load the SignUp page FXML
        try {
           helper.navigateToScene("/org/example/library_management_system/register.fxml", event);
        } catch (IOException e) {
            e.printStackTrace();  // Handle error if signup.fxml is not found
        }
    }
}
