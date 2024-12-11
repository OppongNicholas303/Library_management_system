package org.example.library_management_system.controllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.entities.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for BooksViewController.
 */
class BooksViewControllerTest {

    private BooksViewController booksViewController;

    private TableView<Book> mockBooksTable;
    private TableColumn<Book, Integer> mockBookIdColumn;
    private TableColumn<Book, String> mockTitleColumn;
    private TableColumn<Book, String> mockAuthorColumn;
    private TableColumn<Book, Boolean> mockAvailabilityColumn;

    @BeforeAll
    static void setupAll() throws InterruptedException{
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }

    @BeforeEach
    void setUp() {
        booksViewController = new BooksViewController();
        mockBooksTable = new TableView<>();
        mockBookIdColumn = new TableColumn<>();
        mockTitleColumn = new TableColumn<>();
        mockAuthorColumn = new TableColumn<>();
        mockAvailabilityColumn = new TableColumn<>();

        booksViewController.booksTable = mockBooksTable;
        booksViewController.bookIdColumn = mockBookIdColumn;
        booksViewController.titleColumn = mockTitleColumn;
        booksViewController.authorColumn = mockAuthorColumn;
        booksViewController.availabilityColumn = mockAvailabilityColumn;
    }

    @Test
    void testInitialize() {
        // Mock the PropertyValueFactory and verify column setup
        booksViewController.initialize();

        verify(mockBookIdColumn).setCellValueFactory(any(PropertyValueFactory.class));
        verify(mockTitleColumn).setCellValueFactory(any(PropertyValueFactory.class));
        verify(mockAuthorColumn).setCellValueFactory(any(PropertyValueFactory.class));
        verify(mockAvailabilityColumn).setCellValueFactory(any(PropertyValueFactory.class));
    }

    @Test
    void testLoadBooks() throws Exception {
        // Mock database connection and query results
        Connection mockConnection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(DatabaseConnection.getConnection()).thenReturn(mockConnection);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery("SELECT itemId, title, author, availability_status FROM libraryItem"))
                .thenReturn(mockResultSet);

        // Mock result set data
        when(mockResultSet.next()).thenReturn(true, true, false); // Two books in result set
        when(mockResultSet.getInt("itemId")).thenReturn(1, 2);
        when(mockResultSet.getString("title")).thenReturn("Book One", "Book Two");
        when(mockResultSet.getString("author")).thenReturn("Author One", "Author Two");
        when(mockResultSet.getBoolean("availability_status")).thenReturn(true, false);

        // Use a mocked static instance for DatabaseConnection
        try (MockedStatic<DatabaseConnection> mockedStatic = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(mockConnection);

            booksViewController.initialize();
            booksViewController.loadBooks();

            // Verify that books were loaded into the table
            ObservableList<Book> observableBooksList = booksViewController.booksTable.getItems();
            assertNotNull(observableBooksList);
            assertEquals(2, observableBooksList.size());

            Book firstBook = observableBooksList.get(0);
            assertEquals(1, firstBook.getItemId());
            assertEquals("Book One", firstBook.getTitle());
            assertEquals("Author One", firstBook.getAuthor());
            assertTrue(firstBook.isAvailability());

            Book secondBook = observableBooksList.get(1);
            assertEquals(2, secondBook.getItemId());
            assertEquals("Book Two", secondBook.getTitle());
            assertEquals("Author Two", secondBook.getAuthor());
            assertFalse(secondBook.isAvailability());
        }

        // Verify database interactions
        verify(mockConnection).createStatement();
        verify(mockStatement).executeQuery("SELECT itemId, title, author, availability_status FROM libraryItem");
    }
}
