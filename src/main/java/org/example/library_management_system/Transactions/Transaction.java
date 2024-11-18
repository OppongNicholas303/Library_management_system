package org.example.library_management_system.Transactions;

import java.time.LocalDate;
import java.sql.Date;

public class Transaction {
    private int transactionId;
    private int patronId;
    private int itemId;
    private String transactionType; // borrow or return
    private Date transactionDate; // Use LocalDate for handling dates
    private Date dueDate; // Use LocalDate for handling dates
    private boolean returned;

    // Constructor
    public Transaction(int patronId, int itemId, String transactionType, Date transactionDate, Date dueDate, boolean returned) {
        this.patronId = patronId;
        this.itemId = itemId;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.dueDate = dueDate;
        this.returned = returned;
    }

    // Getters and Setters
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getPatronId() {
        return patronId;
    }

    public void setPatronId(int patronId) {
        this.patronId = patronId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
