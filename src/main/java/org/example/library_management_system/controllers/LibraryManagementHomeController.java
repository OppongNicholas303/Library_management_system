package org.example.library_management_system.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.library_management_system.utils.Helper;

import java.io.IOException;

/**
 * Controller class responsible for managing the home page of the library management system.
 * It handles the actions for navigating to the login and signup pages.
 */
public class LibraryManagementHomeController {

    private final Helper helper = new Helper();  // Helper object for utility tasks like navigation

    /**
     * Handles the login button click event. It loads the login page.
     *
     * @param event The ActionEvent triggered when the login button is clicked.
     */
    @FXML
    private void handleLogin(ActionEvent event) {
        try {
            // Navigate to the login page
            helper.navigateToScene("/org/example/library_management_system/login.fxml", event);
        } catch (IOException e) {
            e.printStackTrace();  // Log error if login.fxml is not found
        }
    }

    /**
     * Handles the signup button click event. It loads the signup page.
     *
     * @param event The ActionEvent triggered when the signup button is clicked.
     */
    @FXML
    public void handleSignup(ActionEvent event) {
        try {
            // Navigate to the signup page
            helper.navigateToScene("/org/example/library_management_system/register.fxml", event);
        } catch (IOException e) {
            e.printStackTrace();  // Log error if register.fxml is not found
        }
    }
}
