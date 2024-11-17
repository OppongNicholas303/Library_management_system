package org.example.library_management_system.entities;

public abstract class LibraryItem {
    private int itemId;
    private String title;
    private String author;
    private String itemType;

    // Constructor

    public LibraryItem(){

    }

    public LibraryItem(int itemId, String title, String author) {
        this.itemId = itemId;
        this.title = title;
        this.author = author;
    }

    // Getters and Setters
    public int getItemId() {
        return itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // Abstract Method for Polymorphism
    public abstract void displayInfo();
}
