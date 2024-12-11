package org.example.library_management_system.controllers;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.library_management_system.services.Librarian;
import org.example.library_management_system.utils.Helper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AddTransactionControllerTest {
    Librarian librarian;
    Helper helper;
    AddTransactionController controller;


    @BeforeAll
    static void setupAll() throws InterruptedException{
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }

    @BeforeEach
    void setup(){
        librarian = mock(Librarian.class);
        helper = mock(Helper.class);

        TextField patronIdField = new TextField();
        TextField itemIdField = new TextField();
        TextField transactionTypeField = new TextField();
        DatePicker transactionDateField = new DatePicker();
        DatePicker dueDateField = new DatePicker();

        controller = new AddTransactionController(
                patronIdField,
                itemIdField,
                transactionTypeField,
                transactionDateField,
                dueDateField
        );

        controller.helper = helper;
        controller.librarian = librarian;
    }

    @Test
    void testHandleSubmit_SuccefullySubmitted() throws SQLException {

        controller.patronIdField.setText("1");
        controller.itemIdField.setText("2");
        controller.transactionTypeField.setText("Borrow");
        LocalDate transactionDate = LocalDate.of(2023, 5, 15);
        controller.transactionDateField.setValue(transactionDate);
        controller.dueDateField.setValue(transactionDate);


        when(librarian.makeTransaction(anyString(), anyString(), anyString(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(true);

        controller.handleSubmitTransaction();

        verify(helper).showAlert(Alert.AlertType.INFORMATION, "Transaction Added", "Transaction added successfully!");

    }

    @Test
    void testHandleSubmit_SubmittedFailed () throws SQLException {
        controller.patronIdField.setText("1");
        controller.itemIdField.setText("2");
        controller.transactionTypeField.setText("Borrow");
        LocalDate transactionDate = LocalDate.of(2023, 5, 15);
        controller.transactionDateField.setValue(transactionDate);
        controller.dueDateField.setValue(transactionDate);


        when(librarian.makeTransaction(anyString(), anyString(), anyString(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(false);

        controller.handleSubmitTransaction();

        verify(helper).showAlert(Alert.AlertType.ERROR, "Submission Failed", "An error occurred while adding the transaction.");
    }
    @Test
    void testHandleSubmit_AllFieldsEmpty(){
        controller.patronIdField.setText("");
        controller.itemIdField.setText("");
        controller.transactionTypeField.setText("");
//        LocalDate transactionDate = LocalDate.of("");
        controller.transactionDateField.setValue(null);
        controller.dueDateField.setValue(null);

        controller.handleSubmitTransaction();

        verify(helper).showAlert(Alert.AlertType.ERROR, "Submission Failed", "Please fill in all fields.");
    }
}