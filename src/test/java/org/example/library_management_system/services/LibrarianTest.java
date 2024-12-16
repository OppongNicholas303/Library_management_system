package org.example.library_management_system.services;

import org.example.library_management_system.Transactions.Reservation;
import org.example.library_management_system.controllers.JavaFXTest;
import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.entities.Book;
import org.example.library_management_system.entities.Magazine;
import org.example.library_management_system.entities.Patron;
import org.example.library_management_system.utils.Helper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LibrarianTest extends JavaFXTest {

    @Mock
    private Helper helper;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private Librarian librarian;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        librarian = new Librarian();

        // Set up DatabaseConnection mock
        try (var mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(connection);
        }

        // Default mock behaviors
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
    }

    @Test
    void addItemToDatabase_Book_Success() throws SQLException {
        // Arrange
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890");
        book.setPublicationYear("2024");
        book.setItemType("Book");

        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);


        boolean result = librarian.addItemToDatabase(book);

        assertTrue(result);
    }

    @Test
    void addItemToDatabase_Magazine_Success() throws SQLException {
        // Arrange
        Magazine magazine = new Magazine();
        magazine.setTitle("Test Magazine");
        magazine.setAuthor("Test Publisher");
        magazine.setIssueNumber("123");
        magazine.setItemType("Magazine");

        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        // Act
        boolean result = librarian.addItemToDatabase(magazine);

        // Assert
        assertTrue(result);
        //verify(preparedStatement, times(2)).executeUpdate();
    }

    @Test
    void addPatron_Success() throws SQLException {
        Patron patron = new Patron();
        patron.setFirstName("John");
        patron.setLastName("Doe");
        patron.setEmail("john@examplee.com");
        patron.setContact("1234567890");

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = librarian.addPatron(patron);

        assertTrue(result);
        //verify(preparedStatement).executeUpdate();
    }

    @Test
    void testException(){
        Exception exception = assertThrows(Exception.class, () -> {
            Helper helperMock = mock(Helper.class);
            PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
            when(helperMock.performQuery(anyString(), true ,anyString(), anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeUpdate()).thenReturn(0);
        });

        librarian.addItemToDatabase(new Book());
        librarian.addItemToDatabase(new Magazine());

        assertNotNull(exception);
    }



}