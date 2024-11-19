package org.example.library_management_system.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.dto.Transaction;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class TransactionController {
    Transaction transaction;
    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private TableColumn<Transaction, String> patronNameColumn;

    @FXML
    private TableColumn<Transaction, String> transactionDateColumn;

    @FXML
    private TableColumn<Transaction, String> itemTitleColumn;

    @FXML
    private TableColumn<Transaction, String> dueDateColumn;

    @FXML
    private TableColumn<Transaction, String> transactionTypeColumn;

    private ObservableList<Transaction> transactionList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up TableView columns
        patronNameColumn.setCellValueFactory(new PropertyValueFactory<>("patronName"));
        transactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        itemTitleColumn.setCellValueFactory(new PropertyValueFactory<>("itemTitle"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        transactionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));

        // Load data into the TableView
        loadTransactions();
    }

    private void loadTransactions() {
        String query = """
            SELECT 
                p.firstName AS firstName,
                p.lastName AS lastName,
                t.transactionDate,
                li.title AS title,
                t.dueDate,
                t.transactionType
            FROM 
                Transaction t
            JOIN 
                Patron p ON t.patronId = p.patronId
            JOIN 
                LibraryItem li ON t.itemId = li.itemId;
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Process the result set and populate the ObservableList
            while (resultSet.next()) {
                String patronName = resultSet.getString("firstName") + " " + resultSet.getString("lastName");
                String transactionDate = resultSet.getString("transactionDate");
                String itemTitle = resultSet.getString("title");
                String dueDate = resultSet.getString("dueDate");
                String transactionType = resultSet.getString("transactionType");

                transactionList.add(new Transaction(patronName, transactionDate, transactionType, itemTitle, dueDate));
            }

            // Set the data to the TableView
            transactionTable.setItems(transactionList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
