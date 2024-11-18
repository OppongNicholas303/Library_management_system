package org.example.library_management_system.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.entities.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;

public class BooksViewController {

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, Boolean> availabilityColumn;

    // Replace ObservableList with LinkedList Queue
    private final Queue<Book> booksQueue = new LinkedList<>();

    @FXML
    public void initialize() {
        // Bind the TableColumn to Book fields
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        // Load books from the database into the queue
        loadBooks();
    }

    private void loadBooks() {
        try {
            // Get database connection
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();

            // Get data from the database
            ResultSet resultSet = statement.executeQuery("SELECT title, author, availability_status FROM libraryItem");

            // Enqueue books into the Queue
            while (resultSet.next()) {
                Book book = new Book();
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setAvailability(resultSet.getBoolean("availability_status"));
                booksQueue.add(book);  // Enqueue the book
            }

            // Transfer books from the queue to the TableView
            while (!booksQueue.isEmpty()) {
                booksTable.getItems().add(booksQueue.remove()); // Dequeue and add to TableView
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
