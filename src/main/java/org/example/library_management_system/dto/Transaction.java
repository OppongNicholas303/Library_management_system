package org.example.library_management_system.dto;

public class Transaction {
    String patronName;
    String transactionDate;
    String transactionType;
    String itemTitle;
    String dueDate;

    public Transaction(String patronName, String transactionDate, String transactionType, String itemTitle, String dueDate) {
        this.patronName = patronName;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.itemTitle = itemTitle;
        this.dueDate = dueDate;
    }

    public String getPatronName() {
        return patronName;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getDueDate() {
        return dueDate;
    }
}
