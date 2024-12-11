package org.example.library_management_system.controllers;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.library_management_system.entities.Patron;
import org.example.library_management_system.services.Librarian;
import org.example.library_management_system.utils.Helper;
import org.example.library_management_system.utils.Validation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddPatronControllerTest {
    AddPatronController addPatronController;
    Librarian librarianMock;
    Patron patronMock;
    Helper helperMock;
    Validation validationMock;


    @BeforeAll
    static void setupAll() throws InterruptedException{
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }

    @BeforeEach
    void setUp() {
        librarianMock = mock(Librarian.class);
        patronMock = mock(Patron.class);
        helperMock = mock(Helper.class);
        validationMock = mock(Validation.class);
        addPatronController = new AddPatronController();

        addPatronController.emailField = new TextField();
        addPatronController.contactInfoField = new TextField();
        addPatronController.lastNameField = new TextField();
        addPatronController.firstNameField = new TextField();

        addPatronController.helper = helperMock;
        addPatronController.validation = validationMock;
        addPatronController.patron = patronMock;
        addPatronController.librarian = librarianMock;

    }

    @Test
    void testHandleSubmit_SuccessfulSubmission(){

        when(patronMock.getEmail()).thenReturn("john@doe.com");
        when(patronMock.getContact()).thenReturn("0243911336");
        when(patronMock.getLastName()).thenReturn("Doe");
        when(patronMock.getFirstName()).thenReturn("John");


        when(validationMock.isValidEmail("john@doe.com")).thenReturn(true);
        when(librarianMock.addPatron(any(Patron.class))).thenReturn(true);

        addPatronController.handleSubmit();

        verify(helperMock).showAlert(Alert.AlertType.INFORMATION, "Success", "Patron has been added successfully!");
        verify(helperMock).clearFields(addPatronController.firstNameField, addPatronController.lastNameField, addPatronController.emailField, addPatronController.contactInfoField);

    }

    @Test
    void testHandleSubmit_FailedSubmission(){
        when(patronMock.getEmail()).thenReturn("john@doe.com");
        when(patronMock.getContact()).thenReturn("0243911336");
        when(patronMock.getLastName()).thenReturn("Doe");
        when(patronMock.getFirstName()).thenReturn("John");

        when(validationMock.isValidEmail("john@doe.com")).thenReturn(true);
        when(librarianMock.addPatron(any(Patron.class))).thenReturn(false);

        addPatronController.handleSubmit();

        verify(helperMock).showAlert(Alert.AlertType.ERROR, "Submission Failed", "An error occurred. Please try again.");
    }

    @Test
    void testHandleSubmit_InvalidEmail(){
        when(patronMock.getEmail()).thenReturn("invalid-email");
        when(patronMock.getContact()).thenReturn("0243911336");
        when(patronMock.getLastName()).thenReturn("Doe");
        when(patronMock.getFirstName()).thenReturn("John");

        when(validationMock.isValidEmail("invalid-email")).thenReturn(false);
        addPatronController.handleSubmit();

        verify(helperMock).showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
    }

    @Test
    void testHandleSubmit_EmptyFields(){
        when(patronMock.getEmail()).thenReturn("");
        when(patronMock.getContact()).thenReturn("");
        when(patronMock.getLastName()).thenReturn("");
        when(patronMock.getFirstName()).thenReturn("");

        addPatronController.handleSubmit();

        verify(helperMock).showAlert(Alert.AlertType.ERROR, "Submission Failed", "Please fill in all fields.");
    }


}