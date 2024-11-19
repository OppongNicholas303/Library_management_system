package org.example.library_management_system.dto;

public class Reservation {
    private String title;
    private String patronName;
    private String reservationDate;

    public Reservation(String patronName, String title, String transactionDate) {
        this.title = title;
        this.patronName = patronName;
        this.reservationDate = transactionDate;
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
}
