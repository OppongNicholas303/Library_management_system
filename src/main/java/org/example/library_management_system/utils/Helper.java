package org.example.library_management_system.utils;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.example.library_management_system.database.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class Helper {
    private final double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
    private final double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

    public void setWidthAndHeight(Stage stage){
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
    }

    public String hashPassword(String password){
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    public boolean comparePassword(String plainPassword, String hashedPassword){
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    // Utility method to show alert dialogs
    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void navigateToScene(String location, ActionEvent event) throws IOException {
        // Create a new FXMLLoader instance and load the login.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        Parent loginPage = loader.load();  // Load the login page

        // Get the current stage (from the event or elsewhere)
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        this.setWidthAndHeight(stage);

        // Create a new scene with the login page and set it as the stage
        Scene loginScene = new Scene(loginPage);
        stage.setScene(loginScene);  // Set the new scene
        stage.setMaximized(true);
        stage.show();  // Display the stage
    }


    public PreparedStatement performQuery(String query, boolean returnGeneratedKeys, String... fields) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();

        // Decide whether to request generated keys
        PreparedStatement preparedStatement = returnGeneratedKeys
                ? connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
                : connection.prepareStatement(query);

        // Set the parameters
        for (int i = 0; i < fields.length; i++) {
            preparedStatement.setString(i + 1, fields[i]);
        }

        return preparedStatement;
    }

    public void clearFields(TextField... fields) {
        for(TextField field: fields){
            field.clear();
        }
    }

}

