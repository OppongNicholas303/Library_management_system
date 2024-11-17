package org.example.library_management_system.Transactions;

import org.example.library_management_system.entities.LibraryItem;
import org.example.library_management_system.entities.Patron;

import java.util.Date;

public class Transaction {
    private int transactionId;
    private Patron patron;
    private LibraryItem item;
    private String transactionType; // borrow or return
    private Date transactionDate;
    private Date dueDate;
    private boolean returned;

    // Constructor
    public Transaction(int transactionId, Patron patron, LibraryItem item, String transactionType, Date transactionDate, Date dueDate) {
        this.transactionId = transactionId;
        this.patron = patron;
        this.item = item;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.dueDate = dueDate;
        this.returned = false;
    }

    // Getters and Setters
    // Add methods to calculate fines, etc.
}

