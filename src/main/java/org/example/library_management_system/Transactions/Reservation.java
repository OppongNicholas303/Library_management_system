package org.example.library_management_system.Transactions;

import java.time.LocalDate;

public class Reservation {
    private int reservationId;
    private int patronID;
    private int itemID;
    private LocalDate reservationDate;

    // Constructor
    public Reservation(){

    }

    public Reservation(int reservationId, int patronID, int itemID, LocalDate reservationDate) {
        this.reservationId = reservationId;
        this.patronID = patronID;
        this.itemID = itemID;
        this.reservationDate = reservationDate;
    }

    // Getters and Setters

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getPatronId() {
        return patronID;
    }

    public void setPatron(int patronID) {
        this.patronID = patronID;
    }

    public int getItemID( ) {
        return itemID;
    }

    public void setItem(int item) {
        this.itemID = item;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }
}

