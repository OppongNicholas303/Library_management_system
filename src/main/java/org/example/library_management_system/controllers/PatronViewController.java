package org.example.library_management_system.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.entities.Patron;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * Controller class responsible for managing the patron view in the library management system.
 * It handles the display of patrons in a TableView, including retrieving data from the database.
 */
public class PatronViewController {

    @FXML
    private TableView<Patron> patronsTable;  // TableView to display patrons

    @FXML
    private TableColumn<Patron, Integer> patronIdColumn;  // Column for patronId

    @FXML
    private TableColumn<Patron, String> firstNameColumn;  // Column for patron's first name

    @FXML
    private TableColumn<Patron, String> lastNameColumn;  // Column for patron's last name

    @FXML
    private TableColumn<Patron, String> contactColumn;  // Column for patron's contact information

    private final LinkedList<Patron> patronsList = new LinkedList<>();  // List to hold patron data

    /**
     * Initializes the controller by setting up columns in the TableView and loading patron data.
     */
    public void initialize() {
        // Set up cell value factories to bind columns to properties of Patron objects
        patronIdColumn.setCellValueFactory(new PropertyValueFactory<>("patronId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        // Load data from the database into the TableView
        loadPatrons();
    }

    /**
     * Loads patron data from the database and populates the TableView.
     * The data is retrieved using a SQL query.
     */
    private void loadPatrons() {
        try {
            // Establish a connection to the database
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            // SQL query to fetch patron details
            String query = "SELECT patronId, firstName, lastName, contact FROM Patron";
            ResultSet resultSet = statement.executeQuery(query);

            // Process the result set and add Patron objects to the list
            while (resultSet.next()) {
                Patron patron = new Patron();
                patron.setPatronId(resultSet.getInt("patronId"));
                patron.setFirstName(resultSet.getString("firstName"));
                patron.setLastName(resultSet.getString("lastName"));
                patron.setContact(resultSet.getString("contact"));

                patronsList.add(patron);
            }

            // Convert the LinkedList to an ObservableList to populate the TableView
            ObservableList<Patron> observableList = FXCollections.observableArrayList(patronsList);
            patronsTable.setItems(observableList);
        } catch (Exception e) {
            e.printStackTrace();  // Log any errors encountered during data loading
        }
    }
}
