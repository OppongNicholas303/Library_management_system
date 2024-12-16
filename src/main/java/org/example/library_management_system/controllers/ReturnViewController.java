package org.example.library_management_system.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.library_management_system.services.Librarian;
import org.example.library_management_system.utils.Helper;

import java.sql.SQLException;

/**
 * Controller class for handling the return of library items by patrons.
 * It manages the logic of processing returned items.
 */
public class ReturnViewController {

    @FXML
    public TextField patronIdField;  // Input field for patron ID

    @FXML
    public TextField itemIdField;  // Input field for item ID

    Helper helper = new Helper();
    Librarian librarian = new Librarian();

    /**
     * Handles the return item process when the "Return" button is clicked.
     * It validates the input fields and calls the Librarian service to process the return.
     * If successful, shows a success message, otherwise shows an error alert.
     *
     * @param event The action event triggered by clicking the "Return" button.
     */
    @FXML
    public void handleReturn(ActionEvent event) {
        Helper helper = new Helper();
        Librarian librarian = new Librarian();

        // Retrieve input values from the TextFields
        String patronId = patronIdField.getText();
        String itemId = itemIdField.getText();

        // Validate that neither patronId nor itemId is empty
        if (patronId.isEmpty() || itemId.isEmpty()) {
            helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "Please fill in all fields.");
        } else {
            try {
                // Call the returnItem method to process the item return
                boolean isUpdated = librarian.returnItem(itemId, patronId);

                // Show success or failure alert based on the result
                if (isUpdated) {
                    helper.showAlert(Alert.AlertType.INFORMATION, "Submission Successful", "Item returned successfully.");
                } else {
                    helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "An error occurred. Please try again.");
                }
            } catch (SQLException e) {
                // Handle SQL exceptions during the return process
                throw new RuntimeException(e);
            }
        }
    }
}
