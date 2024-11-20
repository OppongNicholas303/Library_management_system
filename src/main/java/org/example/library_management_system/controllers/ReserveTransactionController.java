package org.example.library_management_system.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.library_management_system.Transactions.Reservation;
import org.example.library_management_system.services.Librarian;
import org.example.library_management_system.utils.Helper;
//import org.example.library_management_system.models.Patron;
//import org.example.library_management_system.models.LibraryItem;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class ReserveTransactionController {

    @FXML
    private TextField patronIdField;

    @FXML
    private TextField itemIdField;

    @FXML
    private DatePicker reservationDateField;

    // Method to handle reservation logic when the "Reserve" button is clicked
    @FXML
    private void handleReserveItem() {
        Reservation reservation = new Reservation();
        Librarian librarian = new Librarian();
        Helper helper = new Helper();
        reservation.setPatron(Integer.parseInt(patronIdField.getText()));
        reservation.setItem(Integer.parseInt(itemIdField.getText()));
        reservation.setReservationDate(reservationDateField.getValue());

        try {
            boolean isUpload = librarian.makeReservation(reservation);
            if (isUpload) {
                helper.showAlert(Alert.AlertType.INFORMATION, "Success", "Item has reserve successfully!");
                helper.clearFields(patronIdField, itemIdField);
            }else {
                helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "An error occurred. Please try again.");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    }
