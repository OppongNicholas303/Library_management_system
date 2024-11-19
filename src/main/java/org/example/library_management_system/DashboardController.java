package org.example.library_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class DashboardController {

    public Button addMagazineButton;

    public Button borrowItem;
    @FXML
    private StackPane contentArea;

    @FXML
    private Label welcomeLabel;

    @FXML
    private void handleBooksTab() {
        displayContent("/org/example/library_management_system/bookView.fxml");
    }

    @FXML
    private void handleMagazinesTab() {
        displayContent("/org/example/library_management_system/magazineView.fxml");
    }

    @FXML
    private void handleReservationsTab() {
        displayContent("/org/example/library_management_system/reservationTable.fxml");
    }

    @FXML
    private void handleTransactionsTab() {
        displayContent("/org/example/library_management_system/transactionView.fxml");
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

    public void handleAddMagazineTab(ActionEvent actionEvent) {
        displayContent("/org/example/library_management_system/magazine.fxml");
    }

    public void handleBorrowItemTab(ActionEvent actionEvent) {
        displayContent("/org/example/library_management_system/borrowItem.fxml");
    }

    public void handleReserveItemTab(ActionEvent actionEvent) {

        displayContent("/org/example/library_management_system/reserveView.fxml");
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
