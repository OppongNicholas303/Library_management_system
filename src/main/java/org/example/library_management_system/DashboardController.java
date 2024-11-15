package org.example.library_management_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class DashboardController {

    @FXML
    private StackPane contentArea;

    @FXML
    private Label welcomeLabel;

    @FXML
    private void handleBooksTab() {
        displayContent("Books Section");
    }

    @FXML
    private void handleMagazinesTab() {
        displayContent("Magazines Section");
    }

    @FXML
    private void handleReservationsTab() {
        displayContent("Reservations Section");
    }

    @FXML
    private void handleTransactionsTab() {
        displayContent("Transactions Section");
    }

    @FXML
    private void handlePatronsTab() {
        displayContent("Patrons Section");
    }

    @FXML
    private void handleAddPatronTab() {
        displayContent("/org/example/library_management_system/addPatron.fxml");
    }

    @FXML
    private void handleAddBookTab() {
        displayContent("/org/example/library_management_system/addBook.fxml");
    }

    private void displayContent(String fxmlPath) {
        try {
            contentArea.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent loadedContent = loader.load();
            contentArea.getChildren().add(loadedContent);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to load content: " + fxmlPath);
        }
    }

    // Utility method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
