package org.example.library_management_system.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import org.example.library_management_system.entities.Book;
import org.example.library_management_system.entities.Magazine;
import org.example.library_management_system.services.Librarian;
import org.example.library_management_system.utils.Helper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.control.TextField;


import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddBookControllerTest extends JavaFXTest{

    Helper helperMock ;
    Librarian librarianMock;
    AddBookController addBook;
    Book bookMock;

    @BeforeEach
    void setUp() {
        helperMock = mock(Helper.class);
        librarianMock = mock(Librarian.class);
        bookMock = mock(Book.class);
        addBook = new AddBookController();

        addBook.itemTypeField = new TextField();
        addBook.titleField = new TextField();
        addBook.publishedDate = new TextField();
        addBook.isbnField = new TextField();
        addBook.authorField = new TextField();

        addBook.helper = helperMock;
        addBook.librarian = librarianMock;
        addBook.book = bookMock;

    }

    @Test
    void testHandleSubmit_SuccessfulSubmission(){
        when(bookMock.getTitle()).thenReturn("Book Title");
        when(bookMock.getItemType()).thenReturn("Book Type");
        when(bookMock.getIsbn()).thenReturn("ISBN");
        when(bookMock.getPublicationYear()).thenReturn("2020-10-15");
        when(bookMock.getAuthor()).thenReturn("Peter");


        when(librarianMock.addItemToDatabase(any(Book.class))).thenReturn(true);

        addBook.handleSubmit();

        verify(helperMock).showAlert(Alert.AlertType.INFORMATION, "Success", "Book has been added successfully!");
    }

    @Test
    void testHandleSubmit_SubmissionFailed(){
        when(bookMock.getTitle()).thenReturn("Book Title");
        when(bookMock.getItemType()).thenReturn("Book Type");
        when(bookMock.getIsbn()).thenReturn("ISBN");
        when(bookMock.getPublicationYear()).thenReturn("2020-10-15");
        when(bookMock.getAuthor()).thenReturn("Peter");

        when(librarianMock.addItemToDatabase(any(Book.class))).thenReturn(false);
        addBook.handleSubmit();
        verify(helperMock).showAlert(Alert.AlertType.ERROR, "Submission Failed", "An error occurred. Please try again.");
    }

    @Test
    void testHandleSubmit_EmptyFields(){
        when(bookMock.getTitle()).thenReturn("");
        when(bookMock.getItemType()).thenReturn("");
        when(bookMock.getIsbn()).thenReturn("");
        when(bookMock.getPublicationYear()).thenReturn("");
        when(bookMock.getAuthor()).thenReturn("");

        addBook.handleSubmit();

        verify(helperMock).showAlert(Alert.AlertType.ERROR, "Submission Failed", "Please fill in all fields.");
    }

}