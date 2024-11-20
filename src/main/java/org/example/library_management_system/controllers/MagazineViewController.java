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

/**
 * Controller class responsible for managing the magazine view in the library management system.
 * It handles the display of magazines in a TableView, including retrieving data from the database.
 */
public class MagazineViewController {

    @FXML
    private TableView<Magazine> magazinesTable;  // TableView to display magazines

    @FXML
    private TableColumn<Magazine, Integer> magazineIdColumn;  // Column for magazineId

    @FXML
    private TableColumn<Magazine, String> titleColumn;  // Column for magazine title

    @FXML
    private TableColumn<Magazine, String> authorColumn;  // Column for magazine author

    @FXML
    private TableColumn<Magazine, String> issueNumberColumn;  // Column for magazine issue number

    @FXML
    private TableColumn<Magazine, Boolean> availabilityColumn;  // Column for magazine availability

    private final LinkedList<Magazine> magazinesList = new LinkedList<>();  // List to hold magazine data

    /**
     * Initializes the controller by setting up columns in the TableView and loading data.
     */
    public void initialize() {
        // Set up cell value factories to bind columns to properties of Magazine objects
        magazineIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        issueNumberColumn.setCellValueFactory(new PropertyValueFactory<>("issueNumber"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        // Load data from the database into the TableView
        loadMagazines();
    }

    /**
     * Loads magazines data from the database and populates the TableView.
     * The data is retrieved using a SQL query that joins the LibraryItem and Magazine tables.
     */
    private void loadMagazines() {
        try {
            // Establish a connection to the database
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            // SQL query to fetch magazine details
            String query = "SELECT li.itemId, li.title, li.author, m.issueNumber, li.availability_status " +
                           "FROM LibraryItem li " +
                           "JOIN Magazine m ON li.itemId = m.magazineId";
            ResultSet resultSet = statement.executeQuery(query);

            // Process the result set and add Magazine objects to the list
            while (resultSet.next()) {
                Magazine magazine = new Magazine();
                magazine.setItemId(resultSet.getInt("itemId"));
                magazine.setTitle(resultSet.getString("title"));
                magazine.setAuthor(resultSet.getString("author"));
                magazine.setIssueNumber(resultSet.getString("issueNumber"));
                magazine.setAvailability(resultSet.getBoolean("availability_status"));

                magazinesList.add(magazine);
            }

            // Convert the LinkedList to an ObservableList to populate the TableView
            ObservableList<Magazine> observableList = FXCollections.observableArrayList(magazinesList);
            magazinesTable.setItems(observableList);
        } catch (Exception e) {
            e.printStackTrace();  // Log any errors encountered during data loading
        }
    }
}
