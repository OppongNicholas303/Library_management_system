package org.example.library_management_system.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.services.Librarian;
import org.example.library_management_system.utils.Helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ReturnViewController {

    @FXML
    private TextField patronIdField;

    @FXML
    private TextField itemIdField;

    @FXML
    private void handleReturn(ActionEvent event) {
        Helper helper = new Helper();
        Librarian librarian = new Librarian();
        // Retrieve input values
        String patronId = patronIdField.getText();
        String itemId = itemIdField.getText();
        System.out.println("Patron Id: " + patronId);
       if(patronId.isEmpty()|| itemId.isEmpty()) {
           helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "Please fill in all fields.");
       }else {
           try {
               boolean isUpdated = librarian.returnItem(itemId, patronId);
               if (isUpdated) {
                   helper.showAlert(Alert.AlertType.ERROR, "Submission successfully", "success.");
               }else {
                   helper.showAlert(Alert.AlertType.ERROR, "Submission Failed", "Please fill in all fields.");
               }
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
       }


    }
}
