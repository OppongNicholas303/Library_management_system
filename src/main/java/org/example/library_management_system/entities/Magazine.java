package org.example.library_management_system.entities;

public class Magazine extends LibraryItem {
    private String issueNumber;

    // Constructor
    public Magazine(){

    }

    public Magazine(int itemId, String title, String author, String issueNumber) {
        super(itemId, title, author);
        this.issueNumber = issueNumber;
    }

    // Getters and Setters
    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    // Polymorphic Method
    @Override
    public void displayInfo() {
        System.out.println("Magazine ID: " + getItemId() +
                "\nTitle: " + getTitle() +
                "\nAuthor: " + getAuthor() +
                "\nIssue Number: " + issueNumber);
    }
}
