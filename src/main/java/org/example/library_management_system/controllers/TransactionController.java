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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * Controller class for managing the transactions in the library system.
 * It handles loading and displaying transaction data in a TableView.
 */
public class TransactionController {

    @FXML
    private TableView<Transaction> transactionTable;  // TableView to display transaction records

    @FXML
    private TableColumn<Transaction, Integer> patronIdColumn;  // Column for Patron ID

    @FXML
    private TableColumn<Transaction, Integer> itemIdColumn;  // Column for Item ID

    @FXML
    private TableColumn<Transaction, String> patronNameColumn;  // Column for Patron's name

    @FXML
    private TableColumn<Transaction, String> transactionDateColumn;  // Column for Transaction Date

    @FXML
    private TableColumn<Transaction, String> itemTitleColumn;  // Column for Item Title

    @FXML
    private TableColumn<Transaction, String> dueDateColumn;  // Column for Due Date

    @FXML
    private TableColumn<Transaction, String> transactionTypeColumn;  // Column for Transaction Type

    private final LinkedList<Transaction> transactionsList = new LinkedList<>();  // List to hold transaction data

    /**
     * Initializes the controller and sets up the TableView columns.
     * It maps the TableView columns to the respective properties in the Transaction model.
     * Then it loads the transaction data from the database.
     */
    @FXML
    public void initialize() {
        // Map TableView columns to Transaction object properties
        patronIdColumn.setCellValueFactory(new PropertyValueFactory<>("patronId"));
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        patronNameColumn.setCellValueFactory(new PropertyValueFactory<>("patronName"));
        transactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        itemTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        transactionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));

        // Load transactions from the database
        loadTransactions();
    }

    /**
     * Loads transaction data from the database and populates the TableView.
     * It executes an SQL query to retrieve transaction details including patron name, item title,
     * transaction date, due date, and transaction type.
     */
    private void loadTransactions() {
        try {
            // Establish connection to the database
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();

            // SQL query to fetch transaction details from the database
            String query = """
                SELECT 
                    p.patronId AS patronId,
                    li.itemId AS itemId,
                    CONCAT(p.firstName, ' ', p.lastName) AS patronName,
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

            // Execute query and process the result set
            ResultSet resultSet = statement.executeQuery(query);

            // Iterate over the result set and create Transaction objects
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setPatronId(resultSet.getInt("patronId"));
                transaction.setItemId(resultSet.getInt("itemId"));
                transaction.setPatronName(resultSet.getString("patronName"));
                transaction.setTransactionDate(resultSet.getString("transactionDate"));
                transaction.setTitle(resultSet.getString("title"));
                transaction.setDueDate(resultSet.getString("dueDate"));
                transaction.setTransactionType(resultSet.getString("transactionType"));

                transactionsList.add(transaction);
            }

            // Convert LinkedList to ObservableList and bind it to the TableView
            ObservableList<Transaction> observableTransactionsList = FXCollections.observableArrayList(transactionsList);
            transactionTable.setItems(observableTransactionsList);

        } catch (Exception e) {
            e.printStackTrace();  // Handle exceptions
        }
    }
}
