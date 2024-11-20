package org.example.library_management_system.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.library_management_system.Transactions.Reservation;
import org.example.library_management_system.services.Librarian;
import org.example.library_management_system.utils.Helper;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Controller class for handling the reservation transaction in the library management system.
 * It manages the process of reserving items by patrons.
 */
public class ReserveTransactionController {

    @FXML
    private TextField patronIdField;  // Input field for patron ID

    @FXML
    private TextField itemIdField;  // Input field for item ID

    @FXML
    private DatePicker reservationDateField;  // DatePicker for selecting reservation date

    /**
     * Handles the logic for reserving an item when the "Reserve" button is clicked.
     * It validates the input fields, creates a new reservation, and processes the reservation through
     * the Librarian service.
     */
    @FXML
    private void handleReserveItem() {
        // Create a new reservation object
        Reservation reservation = new Reservation();
        Librarian librarian = new Librarian();
        Helper helper = new Helper();

        // Set reservation details from the user input
        reservation.setPatron(Integer.parseInt(patronIdField.getText()));  // Set patron ID
        reservation.setItem(Integer.parseInt(itemIdField.getText()));  // Set item ID
        reservation.setReservationDate(reservationDateField.getValue());  // Set reservation date

        try {
            // Call the makeReservation method to process the reservation
            boolean isUpload = librarian.makeReservation(reservation);
            if (isUpload) {
                // If reservation is successful, show success message
                helper.showAlert(Alert.AlertType.INFORMATION, "Success", "Item has been reserved successfully!");
                helper.clearFields(patronIdField, itemIdField);  // Clear input fields
            } else {
                // If reservation fails, show error message
                helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "An error occurred. Please try again.");
            }
        } catch (SQLException ex) {
            // Handle any SQL exceptions
            throw new RuntimeException(ex);
        }
    }
}
