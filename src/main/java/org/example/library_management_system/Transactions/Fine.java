package org.example.library_management_system.Transactions;

import java.util.Date;

public class Fine {
    private int fineId;
    private Transaction transaction;
    private double fineAmount;
    private Date issuedDate;

    // Constructor
    public Fine(int fineId, Transaction transaction, double fineAmount, Date issuedDate) {
        this.fineId = fineId;
        this.transaction = transaction;
        this.fineAmount = fineAmount;
        this.issuedDate = issuedDate;
    }

    public int getFineId() {
        return fineId;
    }

    public void setFineId(int fineId) {
        this.fineId = fineId;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }
}
