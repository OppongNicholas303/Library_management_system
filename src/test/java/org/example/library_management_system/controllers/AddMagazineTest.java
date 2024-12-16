package org.example.library_management_system.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.library_management_system.entities.Magazine;
import org.example.library_management_system.services.Librarian;
import org.example.library_management_system.utils.Helper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddMagazineTest extends JavaFXTest {
    Helper helperMock;
    Librarian librarianMock;
    AddMagazine addMagazine;
    Magazine magazineMock;

//    @BeforeAll
//    static void setupAll() throws InterruptedException{
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.startup(latch::countDown);
//        latch.await();
//    }

    @BeforeEach
    void setUp(){
        helperMock = mock(Helper.class);
        librarianMock = mock(Librarian.class);
        magazineMock = mock(Magazine.class);

        addMagazine = new AddMagazine();

        addMagazine.titleField = new TextField();
        addMagazine.authorField = new TextField();
        addMagazine.issueNumberField = new TextField();
        addMagazine.itemTypeField = new TextField();

        addMagazine.helper = helperMock;
        addMagazine.librarian = librarianMock;
    }

    @Test
    void testHandleSubmit_AllFieldsEmpty(){
        addMagazine.titleField.setText("");
        addMagazine.authorField.setText("");
        addMagazine.issueNumberField.setText("");
        addMagazine.itemTypeField.setText("");

        addMagazine.handleSubmit(mock(ActionEvent.class));

        verify(helperMock).showAlert(
                eq(Alert.AlertType.ERROR),
                eq("Submission Failed"),
                eq("Please fill in all fields.")
        );
    }

    @Test
    void testHandleSubmit_SuccessfulSubmission(){

        addMagazine.titleField.setText("Magazine Title");
        addMagazine.authorField.setText("Nicholas");
        addMagazine.issueNumberField.setText("27272NM");
        addMagazine.itemTypeField.setText("Magazine Type");


        when(librarianMock.addItemToDatabase(any(Magazine.class))).thenReturn(true);

        addMagazine.handleSubmit(mock(ActionEvent.class));

        // Verify the correct alert is shown
        verify(helperMock).showAlert(
                eq(Alert.AlertType.INFORMATION),
                eq("Success"),
                eq("Magazine has been added successfully!")
        );

    }

    @Test
    void testHandleSubmit_SubmissionFailed(){
        addMagazine.titleField.setText("Magazine Title");
        addMagazine.authorField.setText("Nicholas");
        addMagazine.issueNumberField.setText("27272NM");
        addMagazine.itemTypeField.setText("Magazine Type");

        when(librarianMock.addItemToDatabase(any(Magazine.class))).thenReturn(false);

        addMagazine.handleSubmit(mock(ActionEvent.class));

        // Verify the correct alert is shown
        verify(helperMock).showAlert(Alert.AlertType.ERROR, "Submission Failed", "An error occurred. Please try again.");

    }


}