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
import java.util.Stack;

public class ReservationController {

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, String> patronNameColumn;

    @FXML
    private TableColumn<Reservation, String> itemTitleColumn;

    @FXML
    private TableColumn<Reservation, String> reservationDateColumn;

    private final LinkedList<Reservation> reservationList = new LinkedList<>();
    private  final Stack<Reservation> stack = new Stack<>();

    @FXML
    public void initialize() {
        // Map table columns to Reservation properties
       patronNameColumn.setCellValueFactory(new PropertyValueFactory<>("patronName"));
        itemTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        reservationDateColumn.setCellValueFactory(new PropertyValueFactory<>("reservationDate")); // Fix the property name

        // Load reservation data from the database
        loadReservations();
    }

    private void loadReservations() {
       String query = """
               SELECT reservation.reservationDate AS reservationDate,
                      patron.firstName AS patronName,
                      libraryitem.title AS itemTitle
               FROM reservation
               JOIN patron ON reservation.patronId = patron.patronId
               JOIN libraryitem ON reservation.itemId = libraryitem.itemId""";
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Retrieve values from the result set
                String patronName = resultSet.getString("patronName");
                String itemTitle = resultSet.getString("itemTitle");
                String reservationDate = resultSet.getString("reservationDate");

                // Create a new Reservation object
                Reservation reservation = new Reservation(patronName, itemTitle, reservationDate);

                // Add the reservation to the list
                reservationList.add(reservation);
               // stack.push(reservation);
            }

//            while (!stack.isEmpty()) {
//                reservationList.add(stack.pop());
//            }

            // Convert LinkedList to ObservableList and bind it to the TableView
            ObservableList<Reservation> observableReservationList = FXCollections.observableArrayList(reservationList);
            reservationTable.setItems(observableReservationList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
