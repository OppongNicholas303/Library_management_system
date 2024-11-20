package org.example.library_management_system.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.library_management_system.entities.Magazine;
import org.example.library_management_system.services.Librarian;
import org.example.library_management_system.utils.Helper;

/**
 * Controller class for managing the addition of magazines to the library system.
 * Handles user input for magazine details and communicates with the database.
 */
public class AddMagazine {

    public TextField titleField;  // Input for the magazine title

    public TextField authorField;  // Input for the magazine author

    public TextField issueNumberField;  // Input for the magazine issue number

    public TextField itemTypeField;  // Input for the item type (e.g., magazine)

    public Button submitButton;  // Button to submit the form

    Helper helper = new Helper();  // Helper class for utility functions
    Librarian librarian = new Librarian();  // Librarian class to manage database actions

    /**
     * Handles the submission of magazine details. Validates input and adds the magazine to the database.
     * @param actionEvent the event triggered by the submit button
     */
    public void handleSubmit(ActionEvent actionEvent) {

        // Create a new Magazine object and set its properties from the form
        Magazine magazine = new Magazine();
        magazine.setTitle(titleField.getText().trim());
        magazine.setAuthor(authorField.getText().trim());
        magazine.setIssueNumber(issueNumberField.getText());
        magazine.setItemType(itemTypeField.getText());

        // Validate the form fields
        if (magazine.getTitle().isEmpty() || magazine.getAuthor().isEmpty() ||
            magazine.getIssueNumber().isEmpty() || magazine.getItemType().isEmpty()) {
            helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "Please fill in all fields.");
        } else {
            // Attempt to add the magazine to the database
            boolean isUploaded = librarian.addItemToDatabase(magazine);

            // Show appropriate message based on success or failure
            if (isUploaded) {
                helper.showAlert(Alert.AlertType.INFORMATION, "Success", "Magazine has been added successfully!");
                helper.clearFields(titleField, authorField, issueNumberField, itemTypeField);
            } else {
                helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "An error occurred. Please try again.");
            }
        }
    }
}
