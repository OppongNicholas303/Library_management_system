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

public class BooksViewController {

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, Boolean> availabilityColumn;

    private final LinkedList<Book> booksList = new LinkedList<>();

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        loadBooks();
    }

    private void loadBooks() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT title, author, availability_status FROM libraryItem");

            while (resultSet.next()) {
                Book book = new Book();
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setAvailability(resultSet.getBoolean("availability_status"));

                booksList.add(book);
            }

            // Convert LinkedList to ObservableList to set in the TableView
            ObservableList<Book> observableBooksList = FXCollections.observableArrayList(booksList);
            booksTable.setItems(observableBooksList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
