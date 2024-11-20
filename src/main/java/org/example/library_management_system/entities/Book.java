package org.example.library_management_system.entities;

import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.utils.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Book extends LibraryItem {
    private String isbn;
    private String publicationYear;
    private final Helper helper = new Helper();


    // Constructor
    public Book(){}

    public Book(int itemId, String title, String author, String isbn, String publicationYear) {
        super(itemId, title, author);
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    // Getters and Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    // Polymorphic Method
    @Override
    public void displayInfo() {
        System.out.println("Book ID: " + getItemId() +
                "\nTitle: " + getTitle() +
                "\nAuthor: " + getAuthor() +
                "\nGenre: " + isbn +
                "\nPublication Year: " + publicationYear);
    }

}
