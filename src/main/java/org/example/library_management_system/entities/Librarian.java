package org.example.library_management_system.entities;

import org.example.library_management_system.database.DatabaseConnection;
import org.example.library_management_system.utils.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Librarian extends Patron {
    private String password;
    private final Helper helper;
    private Book book;
    private Magazine magazine;

    // Constructor
    public Librarian(){
        this.helper = new Helper();
    }
    public Librarian(int patronId, String firstName, String lastName, String email, String contact, String password, Helper helper) {
        super(patronId, firstName, lastName, email, contact);
        this.password = password;
        this.helper = helper;
    }
    // setters and getters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int register() throws SQLException {
        String hashedPassword = helper.hashPassword(this.password);
        String query = "INSERT INTO librarian (firstName, lastName, contact,  password, email) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = helper.performQuery(query, false, this.getFirstName(), this.getLastName(), this.getContact(), hashedPassword, this.getEmail());
        return  preparedStatement.executeUpdate();
    }

    public void login(){

    }



}

