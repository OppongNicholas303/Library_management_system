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

public class PatronViewController {

    @FXML
    private TableView<Patron> patronsTable;

    @FXML
    private TableColumn<Patron, Integer> patronIdColumn;

    @FXML
    private TableColumn<Patron, String> firstNameColumn;

    @FXML
    private TableColumn<Patron, String> lastNameColumn;

    @FXML
    private TableColumn<Patron, String> contactColumn;

    private final LinkedList<Patron> patronsList = new LinkedList<>();

    public void initialize() {
        // Set cell value factories for TableView columns
        patronIdColumn.setCellValueFactory(new PropertyValueFactory<>("patronId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        // Load data into the TableView
        loadPatrons();
    }

    private void loadPatrons() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT patronId, firstName, lastName, contact FROM Patron";
            ResultSet resultSet = statement.executeQuery(query);

            // Iterate through the result set
            while (resultSet.next()) {
                Patron patron = new Patron();
                patron.setPatronId(resultSet.getInt("patronId"));
                patron.setFirstName(resultSet.getString("firstName"));
                patron.setLastName(resultSet.getString("lastName"));
                patron.setContact(resultSet.getString("contact"));

                patronsList.add(patron);
            }

            // Convert LinkedList to ObservableList and set it in the TableView
            ObservableList<Patron> observableList = FXCollections.observableArrayList(patronsList);
            patronsTable.setItems(observableList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
