package org.example.library_management_system.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.utils.Helper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LibraryLoginControllerTest extends JavaFXTest{
    Helper helperMock;
    LibraryLoginController controller;
    PreparedStatement preparedStmtMock;
    ResultSet resultSetMock;
    Connection connectionMock;

    @BeforeEach
    void setUp() {
        helperMock = mock(Helper.class);
        controller = new LibraryLoginController();
        preparedStmtMock = mock(PreparedStatement.class);
        resultSetMock = mock(ResultSet.class);
        connectionMock = mock(Connection.class);

        controller.backButton = new Button();
        controller.loginButton = new Button();
        controller.passwordField = new PasswordField();
        controller.usernameField = new TextField();

        controller.helper = helperMock;


    }

    @Test
    void testLoginSubmitSuccessfully() throws SQLException, IOException {
        controller.usernameField.setText("admin@gmail.com");
        controller.passwordField.setText("admin");

        String email= controller.usernameField.getText();
        String password =  controller.passwordField.getText();

        when(helperMock.performQuery("SELECT * FROM librarian WHERE email = ? ", false, email)).thenReturn(preparedStmtMock);
        when(preparedStmtMock.executeQuery()).thenReturn(resultSetMock);

        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getString("password")).thenReturn("hashed_password");
        when(helperMock.comparePassword(password, "hashed_password")).thenReturn(true);
        ActionEvent actionEvent = mock(ActionEvent.class);

        controller.handleLogin(actionEvent);

        verify(helperMock).navigateToScene("/org/example/library_management_system/librarianDashboard.fxml", actionEvent);

        controller.handleLogin(actionEvent);

        controller.handleLogin(actionEvent);
        controller.usernameField.setText("");
        controller.passwordField.setText("");

        controller.handleLogin(actionEvent);

        verify(helperMock).showAlert(Alert.AlertType.ERROR, "Registration Failed", "Please fill in all fields.");

    }


    @Test
    void testLoginSubmitFailed() throws SQLException, IOException {
        controller.usernameField.setText("admin@gmail.com");
        controller.passwordField.setText("admin");

        String email= controller.usernameField.getText();
        String password =  controller.passwordField.getText();

        when(helperMock.performQuery("SELECT * FROM librarian WHERE email = ? ", false, email)).thenReturn(preparedStmtMock);
        when(preparedStmtMock.executeQuery()).thenReturn(resultSetMock);

        when(resultSetMock.next()).thenReturn(false);
        ActionEvent actionEvent = mock(ActionEvent.class);

        controller.handleLogin(actionEvent);

        verify(helperMock).showAlert(Alert.AlertType.INFORMATION, "Invalid Credentials", "Email or password is wrong");

    }

    @Test
    void testLoginIncorrectPassword() throws SQLException, IOException {

        controller.usernameField.setText("admin@gmail.com");
        controller.passwordField.setText("admin");

        String email= controller.usernameField.getText();
        String password =  controller.passwordField.getText();

        when(helperMock.performQuery("SELECT * FROM librarian WHERE email = ? ", false, email)).thenReturn(preparedStmtMock);
        when(preparedStmtMock.executeQuery()).thenReturn(resultSetMock);

        when(resultSetMock.next()).thenReturn(true);

        when(helperMock.comparePassword(anyString(), anyString())).thenReturn(false);

        ActionEvent actionEvent = mock(ActionEvent.class);

        controller.handleLogin(actionEvent);

        verify(helperMock).showAlert(Alert.AlertType.INFORMATION, "Invalid Credentials", "Email or password is wrong");
    }

    @Test
    void testException() throws SQLException {
        controller.usernameField.setText("admin@gmail.com");
        controller.passwordField.setText("admin");

        String email= controller.usernameField.getText();


        when(helperMock.performQuery("SELECT * FROM librarian WHERE email = ? ", false, email)).thenThrow(new SQLException());
        ActionEvent actionEvent = mock(ActionEvent.class);
        controller.handleLogin(actionEvent);

        verify(helperMock).showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while processing your request.");

    }




}