package org.example.library_management_system.dto;

public class Reservation {
    private String title;
    private String patronName;
    private String reservationDate;
    private int patronId;

    public Reservation(int patronId, String patronName, String title, String transactionDate ) {
        this.title = title;
        this.patronName = patronName;
        this.reservationDate = transactionDate;
        this.patronId = patronId;
    }

    public String getTitle() {
        return title;
    }

    public String getPatronName() {
        return patronName;
    }

    public String getReservationDate() {
        return reservationDate;
    }
    public int getPatronId() {return patronId;}
}
