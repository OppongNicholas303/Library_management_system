package org.example.library_management_system.utils;

import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class Helper {
    private final double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
    private final double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

    public void setWidthAndHeight(Stage stage){
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
    }
    public String hashPassword(String password){
        return  BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean comparePassword(String plainPassword, String hashedPassword){
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

}

