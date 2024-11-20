package org.example.library_management_system.dto;

public class Transaction {

    private int patronId;
    private int itemId;
    private String patronName;
    private String transactionDate;
    private String title;
    private String dueDate;
    private String transactionType;

    // Constructor
    public Transaction() {  }
    public Transaction(int patronId, int itemId, String patronName, String transactionDate,
                       String transactionType, String title, String dueDate) {
        this.patronId = patronId;
        this.itemId = itemId;
        this.patronName = patronName;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.title = title;
        this.dueDate = dueDate;
    }

    // Getter and Setter methods
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

    public String getPatronName() {
        return patronName;
    }

    public void setPatronName(String patronName) {
        this.patronName = patronName;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
