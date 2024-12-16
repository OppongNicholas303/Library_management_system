package org.example.library_management_system.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BooksViewControllerTest extends JavaFXTest{
    private BooksViewController controller;
    private Connection connectionMock;
    private Statement statementMock;
    private ResultSet resultSetMock;
    private DatabaseConnection databaseConnectionMock;

    @BeforeEach
    void setUp() throws SQLException {
        connectionMock = mock(Connection.class);
        statementMock = mock(Statement.class);
        resultSetMock = mock(ResultSet.class);
        databaseConnectionMock = mock(DatabaseConnection.class);

        when(connectionMock.createStatement()).thenReturn(statementMock);
        when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);

        controller = new BooksViewController();

        controller.booksTable = new TableView<>();
        controller.bookIdColumn = new TableColumn<>();
        controller.titleColumn = new TableColumn<>();
        controller.authorColumn = new TableColumn<>();
        controller.availabilityColumn = new TableColumn<>();
    }

    @Test
    void testLoadBooks() throws SQLException {

        when(resultSetMock.next())
                .thenReturn(true)  // First call returns true
                .thenReturn(false); // Second call returns false to end loop
        when(resultSetMock.getInt("itemId")).thenReturn(1);
        when(resultSetMock.getString("title")).thenReturn("Test Book");
        when(resultSetMock.getString("author")).thenReturn("Test Author");
        when(resultSetMock.getBoolean("availability_status")).thenReturn(true);

        // Mock the static DatabaseConnection.getConnection() call
        try (MockedStatic<DatabaseConnection> dbConnectionMock = mockStatic(DatabaseConnection.class)) {
            dbConnectionMock.when(DatabaseConnection::getConnection).thenReturn(connectionMock);

            controller.loadBooks();

            verify(connectionMock).createStatement();
            verify(statementMock).executeQuery("SELECT itemId, title, author, availability_status FROM libraryItem");
            verify(resultSetMock, times(2)).next();

            // Verify the data was loaded correctly
            List<Book> books = controller.booksList;

            assertEquals(1, books.size());
            Book loadedBook = books.get(0);
            assertEquals(1, loadedBook.getItemId());
            assertEquals("Test Book", loadedBook.getTitle());
            assertEquals("Test Author", loadedBook.getAuthor());
        }
    }

    @Test
    void testInitialize() {
        // Test the initialize method
        controller.initialize();

        // Verify that cell value factories are set correctly
        assertNotNull(controller.bookIdColumn.getCellValueFactory());
        assertNotNull(controller.titleColumn.getCellValueFactory());
        assertNotNull(controller.authorColumn.getCellValueFactory());
        assertNotNull(controller.availabilityColumn.getCellValueFactory());
    }

    @Test( )
    void testErrorHandling(){
        MockedStatic<DatabaseConnection> dbConnectionMock = mockStatic(DatabaseConnection.class);

        try (dbConnectionMock) {
            dbConnectionMock.when(DatabaseConnection::getConnection).thenReturn(connectionMock);
            when(connectionMock.createStatement()).thenReturn(statementMock);
            when(statementMock.executeQuery(anyString())).thenThrow(new SQLException("Failed to execute query"));
            statementMock.executeQuery("any query");
        } catch (SQLException exception) {
            assertEquals("Failed to execute query", exception.getMessage());
        }

    }

}