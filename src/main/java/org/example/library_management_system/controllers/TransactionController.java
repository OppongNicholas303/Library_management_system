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

public class TransactionController {

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private TableColumn<Transaction, Integer> patronIdColumn;

    @FXML
    private TableColumn<Transaction, Integer> itemIdColumn;

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

    private final LinkedList<Transaction> transactionsList = new LinkedList<>();

    @FXML
    public void initialize() {
        // Set cell value factories for TableView columns
        patronIdColumn.setCellValueFactory(new PropertyValueFactory<>("patronId"));
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        patronNameColumn.setCellValueFactory(new PropertyValueFactory<>("patronName"));
        transactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        itemTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        transactionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));

        loadTransactions();
    }

    private void loadTransactions() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
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

            ResultSet resultSet = statement.executeQuery(query);

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

            // Convert LinkedList to ObservableList and set to TableView
            ObservableList<Transaction> observableTransactionsList = FXCollections.observableArrayList(transactionsList);
            transactionTable.setItems(observableTransactionsList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
