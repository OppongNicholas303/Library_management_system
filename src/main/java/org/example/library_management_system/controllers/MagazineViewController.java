package org.example.library_management_system.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.entities.Magazine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

public class MagazineViewController {

    @FXML
    private TableView<Magazine> magazinesTable;

    @FXML
    private TableColumn<Magazine, String> titleColumn;

    @FXML
    private TableColumn<Magazine, String> authorColumn;

    @FXML
    private TableColumn<Magazine, String> issueNumberColumn;

    @FXML
    private TableColumn<Magazine, Boolean> availabilityColumn;

    // Use LinkedList to store magazine data
    private final LinkedList<Magazine> magazinesList = new LinkedList<>();

    @FXML
    public void initialize() {
        // Set up columns
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        issueNumberColumn.setCellValueFactory(new PropertyValueFactory<>("issueNumber"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        // Load data into TableView
        loadMagazines();
    }

    private void loadMagazines() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT li.title, li.author, m.issueNumber, li.availability_status " +
                    "FROM LibraryItem li " +
                    "JOIN Magazine m ON li.itemId = m.magazineId";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Magazine magazine = new Magazine();
                magazine.setTitle(resultSet.getString("title"));
                magazine.setAuthor(resultSet.getString("author"));
                magazine.setIssueNumber(resultSet.getString("issueNumber"));
                magazine.setAvailability(resultSet.getBoolean("availability_status"));

                magazinesList.add(magazine);
            }

            // Convert LinkedList to ObservableList for TableView
            ObservableList<Magazine> observableList = FXCollections.observableArrayList(magazinesList);
            magazinesTable.setItems(observableList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
