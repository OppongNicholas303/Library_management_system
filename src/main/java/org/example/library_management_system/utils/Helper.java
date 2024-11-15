package org.example.library_management_system.utils;

import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;



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

}

