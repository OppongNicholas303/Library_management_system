package org.example.library_management_system.utils;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.library_management_system.controllers.JavaFXTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class HelperTest extends JavaFXTest {
    Helper helper;

    @BeforeEach
    void setUp() {
        helper = new Helper();
    }


    @ParameterizedTest
    @ValueSource(strings = {"oppong", "yeye78", "123456"})
    void testHashPassword(String password) {
        String hashed = helper.hashPassword(password);
        System.out.println(hashed);
        assertTrue(BCrypt.checkpw(password, hashed));
    }

    @Test
    void testPasswordMatches() {
        String hashed = "$2a$10$f4yrGjI9bAH2uk4SZm5/2.d5HspXG9bdcf8oezjQAV7DbzpZfTWZW";
        String plainText = "oppong";
        boolean matches = helper.comparePassword(plainText, hashed);
        assertTrue(matches);
    }

    @Test
    void testPreparedStatement() throws SQLException {
        Helper helperMock = mock(Helper.class);
//        PreparedStatement preparedStatement = mock(PreparedStatement.class);
//        when(helperMock.performQuery(anyString(), anyBoolean(), anyString())).thenReturn(preparedStatement);

        String query = "INSERT INTO patron (name, email) VALUES (?, ?)";
        String[] fields = {"John Doe", "john@example.com"};

        PreparedStatement result = helper.performQuery(query, true, fields);

        assertNotNull(result);



    }

    @Test
    void testClearFields() {
        TextField textField = new TextField();
        TextField textField2 = new TextField();
        textField2.setText("hello");
        textField.setText("hello");

        helper.clearFields(textField, textField2);
        assertEquals("", textField.getText());
        assertEquals("", textField2.getText());
    }


//    @Test
//    void testAlert(){
//        Alert.AlertType alertType = Alert.AlertType.CONFIRMATION;
//        String title = "Information";
//        String message = "Success";
//
//        helper.showAlert(alertType, title, message);
//
//        verify(helper).showAlert(alertType, title, message);
//    }

    @Test
    void testAlert() {
        Alert.AlertType alertType = Alert.AlertType.CONFIRMATION;
        String title = "Information";
        String message = "Success";

        // Ensure the test runs on the JavaFX thread
        Platform.runLater(() -> {
            helper.showAlert(alertType, title, message);
            verify(helper).showAlert(alertType, title, message);
        });

        // Wait for the JavaFX thread to process events
        try {
            Thread.sleep(100); // Adjust the time as needed
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}

