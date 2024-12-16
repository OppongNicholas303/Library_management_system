package org.example.library_management_system.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.library_management_system.Transactions.Reservation;
import org.example.library_management_system.services.Librarian;
import org.example.library_management_system.utils.Helper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReserveTransactionControllerTest extends JavaFXTest {
    ReserveTransactionController controller;
    Reservation reservation;
    Helper helper;
    Librarian librarian;

    @BeforeEach
    void setUp() {
        helper = mock(Helper.class);
        controller = new ReserveTransactionController();
        reservation = mock(Reservation.class);
        librarian = mock(Librarian.class);

        controller.patronIdField = new TextField();
        controller.itemIdField = new TextField();
        controller.reservationDateField = new DatePicker();

        controller.reservation = reservation;
        controller.helper = helper;
        controller.librarian = librarian;

    }

    @Test
    void handleReserveItem_SuccessfulReservation() throws SQLException {
        controller.patronIdField.setText("1");
        controller.patronIdField.setText("1");
        controller.itemIdField.setText("100");
        LocalDate reservationDate = LocalDate.now();
        controller.reservationDateField.setValue(reservationDate);

        when(librarian.makeReservation(any(Reservation.class))).thenReturn(true);

        controller.handleReserveItem();

        verify(helper).showAlert(Alert.AlertType.INFORMATION, "Success", "Item has been reserved successfully!");
    }

    @Test
    void handleReserveItem_FailedReservation() throws SQLException {

        controller.patronIdField.setText("1");
        controller.itemIdField.setText("100");
        controller.reservationDateField.setValue(LocalDate.now());

        when(librarian.makeReservation(any(Reservation.class))).thenReturn(false);

        controller.handleReserveItem();

        verify(librarian).makeReservation(any(Reservation.class));
        verify(helper).showAlert(Alert.AlertType.ERROR, "Submission Failed", "An error occurred. Please try again.");
    }

    @Test
    void handleReserveItem_SQLException() throws SQLException {
        // Arrange
        controller.patronIdField.setText("1");
        controller.itemIdField.setText("100");
        controller.reservationDateField.setValue(LocalDate.now());

        when(librarian.makeReservation(any(Reservation.class))).thenThrow(new SQLException("Database error"));

        // Act & Assert
        try {
            controller.handleReserveItem();
        } catch (RuntimeException ex) {
            verify(librarian).makeReservation(any(Reservation.class));
            verify(helper, never()).showAlert(any(), any(), any());
        }
    }
    @Test
    void handleReserveItem_InvalidInput() throws SQLException {
        // Arrange
        controller.patronIdField.setText("invalid");
        controller.itemIdField.setText("100");
        controller.reservationDateField.setValue(LocalDate.now());

        // Act & Assert
        try {
            controller.handleReserveItem();
        } catch (NumberFormatException ex) {
            verify(librarian, never()).makeReservation(any(Reservation.class));
            verify(helper, never()).showAlert(any(), any(), any());
        }
    }
}