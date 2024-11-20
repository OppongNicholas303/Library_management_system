package org.example.library_management_system.controllers;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.entities.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * Controller class responsible for displaying a list of books in the library system.
 * Retrieves book data from the database and populates a TableView for the user to view.
 */
public class BooksViewController {

    @FXML
    private TableView<Book> booksTable;  // TableView to display books

    @FXML
    private TableColumn<Book, Integer> bookIdColumn;  // Column for book ID

    @FXML
    private TableColumn<Book, String> titleColumn;  // Column for book title

    @FXML
    private TableColumn<Book, String> authorColumn;  // Column for book author

    @FXML
    private TableColumn<Book, Boolean> availabilityColumn;  // Column for book availability status

    private final LinkedList<Book> booksList = new LinkedList<>();  // List to hold books fetched from the database

    /**
     * Initializes the controller and sets up the TableView columns.
     */
    public void initialize() {
        // Set cell value factories to map data to TableView columns
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        loadBooks();  // Load books data from the database
    }

    /**
     * Loads the books from the database and populates the TableView with the data.
     * The data is fetched using a SQL query and added to the booksList.
     */
    private void loadBooks() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT itemId, title, author, availability_status FROM libraryItem");  // SQL query to fetch book data

            // Iterate through the result set and add books to the list
            while (resultSet.next()) {
                Book book = new Book();
                book.setItemId(resultSet.getInt("itemId"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setAvailability(resultSet.getBoolean("availability_status"));

                booksList.add(book);
            }

            // Convert the books list to an ObservableList and set it as the data source for the TableView
            ObservableList<Book> observableBooksList = FXCollections.observableArrayList(booksList);
            booksTable.setItems(observableBooksList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
