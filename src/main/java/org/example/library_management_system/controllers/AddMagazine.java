package org.example.library_management_system.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.library_management_system.entities.Magazine;
import org.example.library_management_system.services.Librarian;
import org.example.library_management_system.utils.Helper;

public class AddMagazine {
    public TextField titleField;

    public TextField authorField;

    public TextField issueNumberField;

    public TextField itemTypeField;

    public Button submitButton;

    Helper helper = new Helper();
    Librarian librarian = new Librarian();

    public void handleSubmit(ActionEvent actionEvent) {

        Magazine magazine = new Magazine();

        magazine.setTitle(titleField.getText().trim());
        magazine.setAuthor(authorField.getText().trim());
        magazine.setIssueNumber(issueNumberField.getText());
        magazine.setItemType(itemTypeField.getText());

        if(magazine.getTitle().isEmpty()
           || magazine.getAuthor().isEmpty()
           || magazine.getIssueNumber().isEmpty()
           || magazine.getItemType().isEmpty()
        ){
            helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "Please fill in all fields.");
        }else {
            boolean isUploaded = librarian.addItemToDatabase(magazine);

            if(isUploaded){
                helper.showAlert(Alert.AlertType.INFORMATION, "Success", "Magazine has been added successfully!");
                helper.clearFields(titleField, authorField, issueNumberField, itemTypeField);
            }else {
                helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "An error occurred. Please try again.");
            }
        }

    }
}
