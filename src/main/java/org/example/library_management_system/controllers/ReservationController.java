package org.example.library_management_system.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.dto.Reservation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * Controller class for handling the reservation view in the library management system.
 * It manages the display of reservation data in a table format.
 */
public class ReservationController {

    @FXML
    private TableView<Reservation> reservationTable; // TableView to display reservations

    @FXML
    private TableColumn<Reservation, Integer> patronIdColumn;  // Column for patron ID

    @FXML
    private TableColumn<Reservation, String> patronNameColumn;  // Column for patron name

    @FXML
    private TableColumn<Reservation, String> itemTitleColumn;  // Column for item title

    @FXML
    private TableColumn<Reservation, String> reservationDateColumn;  // Column for reservation date

    private final LinkedList<Reservation> reservationList = new LinkedList<>();  // List to store reservation data

    /**
     * Initializes the ReservationController by mapping the TableView columns to the
     * properties of the Reservation object and loading the reservation data.
     */
    @FXML
    public void initialize() {
        // Map table columns to Reservation properties
        patronIdColumn.setCellValueFactory(new PropertyValueFactory<>("patronId"));  // Set patronId column
        patronNameColumn.setCellValueFactory(new PropertyValueFactory<>("patronName"));
        itemTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        reservationDateColumn.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));

        // Load reservation data from the database
        loadReservations();
    }

    /**
     * Loads reservation data from the database and populates the TableView with the data.
     */
    private void loadReservations() {
        // SQL query to fetch reservation data, joining patron and libraryitem tables
        String query = """
               SELECT reservation.reservationDate AS reservationDate,
                      patron.patronId AS patronId,
                      patron.firstName AS patronName,
                      libraryitem.title AS itemTitle
               FROM reservation
               JOIN patron ON reservation.patronId = patron.patronId
               JOIN libraryitem ON reservation.itemId = libraryitem.itemId""";

        try {
            // Establish connection to the database
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Iterate through the result set and create Reservation objects
            while (resultSet.next()) {
                int patronId = resultSet.getInt("patronId");
                String patronName = resultSet.getString("patronName");
                String itemTitle = resultSet.getString("itemTitle");
                String reservationDate = resultSet.getString("reservationDate");

                // Create a new Reservation object and add it to the list
                Reservation reservation = new Reservation(patronId, patronName, itemTitle, reservationDate);
                reservationList.add(reservation);
            }

            // Convert the LinkedList to ObservableList and bind it to the TableView
            ObservableList<Reservation> observableReservationList = FXCollections.observableArrayList(reservationList);
            reservationTable.setItems(observableReservationList);

        } catch (Exception e) {
            // Print any exceptions that occur during the process
            e.printStackTrace();
        }
    }
}
