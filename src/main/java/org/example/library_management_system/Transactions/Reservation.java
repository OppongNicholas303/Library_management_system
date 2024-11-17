package org.example.library_management_system.Transactions;

import org.example.library_management_system.entities.LibraryItem;
import org.example.library_management_system.entities.Patron;

import java.util.Date;

public class Reservation {
    private int reservationId;
    private Patron patron;
    private LibraryItem item;
    private Date reservationDate;

    // Constructor
    public Reservation(int reservationId, Patron patron, LibraryItem item, Date reservationDate) {
        this.reservationId = reservationId;
        this.patron = patron;
        this.item = item;
        this.reservationDate = reservationDate;
    }

    // Getters and Setters
}

