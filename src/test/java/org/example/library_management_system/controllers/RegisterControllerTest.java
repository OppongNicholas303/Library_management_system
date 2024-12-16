package org.example.library_management_system.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.library_management_system.entities.Librarian;
import org.example.library_management_system.utils.Helper;
import org.example.library_management_system.utils.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterControllerTest extends JavaFXTest {

    Helper helperMock;
    RegisterController controller;
    Validation validationMock;
    Librarian librarianMock;

    @BeforeEach
    void setUp() {
        helperMock = mock(Helper.class);
        controller = new RegisterController();
        validationMock = mock(Validation.class);
        librarianMock = mock(Librarian.class);

        controller.librarian = librarianMock;
        controller.helper = helperMock;
        controller.validation = validationMock;

        controller.firstNameField = new TextField();
        controller.lastNameField = new TextField();
        controller.emailField = new TextField();
        controller.passwordField = new PasswordField();
        controller.contactField = new TextField();
        controller.backButton = new Button();
        controller.registerButton = new Button();

    }

    @Test
    void testRegister() throws SQLException {
        when(librarianMock.getPassword()).thenReturn("");
        when(librarianMock.getEmail()).thenReturn("");
        when(librarianMock.getFirstName()).thenReturn("");
        when(librarianMock.getLastName()).thenReturn("");
        when(librarianMock.getContact()).thenReturn("");

        controller.handleRegister();
        verify(helperMock).showAlert(Alert.AlertType.ERROR, "Registration Failed", "Please fill in all fields.");

        when(librarianMock.getPassword()).thenReturn("1234567");
        when(librarianMock.getEmail()).thenReturn("in-valid@email.com");
        when(librarianMock.getFirstName()).thenReturn("Yaw");
        when(librarianMock.getLastName()).thenReturn("Kofi");
        when(librarianMock.getContact()).thenReturn("123");

        when(validationMock.isValidEmail("in-valid@email.com")).thenReturn(false);
        when(validationMock.isValidContact("123")).thenReturn(false);

        controller.handleRegister();

        verify(helperMock).showAlert(Alert.AlertType.ERROR, "Mismatch input", "Check your inputs.");

        when(librarianMock.getPassword()).thenReturn("1234567");
        when(librarianMock.getEmail()).thenReturn("yaw@email.com");
        when(librarianMock.getFirstName()).thenReturn("Yaw");
        when(librarianMock.getLastName()).thenReturn("Kofi");
        when(librarianMock.getContact()).thenReturn("0243833994");

        when(validationMock.isValidEmail("yaw@email.com")).thenReturn(true);
        when(validationMock.isValidContact("0243833994")).thenReturn(true);

        when(librarianMock.register()).thenReturn(1);

        controller.handleRegister();

        verify(helperMock).showAlert(  Alert.AlertType.INFORMATION,
                "Registration Successful",
                "Welcome, " + "Yaw" + " " + "Kofi" + "!");

        when(librarianMock.register()).thenThrow(SQLException.class);

        controller.handleRegister();

    }

    @Test
    void testBackButton() throws SQLException, IOException {

        ActionEvent actionEvent = mock(ActionEvent.class);


        controller.handleBack(actionEvent);


        verify(helperMock).navigateToScene("/org/example/library_management_system/homepage.fxml", actionEvent);

        doThrow(new IOException("Navigation failed"))
                .when(helperMock)
                .navigateToScene(anyString(), any(ActionEvent.class));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> controller.handleBack(actionEvent));

        assertInstanceOf(IOException.class, exception.getCause());

    }

}