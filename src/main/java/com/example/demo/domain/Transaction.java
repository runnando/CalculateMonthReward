package com.example.demo.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Transaction {
    @Id
    @SequenceGenerator(
            name = "transaction_sequence",
            sequenceName = "transaction_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "transaction_sequence"
    )
    private Long ID;
    private String name;
    private String userId;
    private LocalDate processDate;
    private double amount;

    public Transaction() {

    }

    public Transaction(Long ID, String name, String userId, LocalDate processDate, double amount) {
        this.ID = ID;
        this.name = name;
        this.userId = userId;
        this.processDate = processDate;
        this.amount = amount;
    }

    public Transaction(String name, String userId, LocalDate processDate, double amount) {
        this.name = name;
        this.userId = userId;
        this.processDate = processDate;
        this.amount = amount;
    }

    public Long getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getUserId() { return userId; }

    public LocalDate getProcessDate() {
        return processDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) { this.userId = userId; }

    public void setProcessDate(LocalDate processDate) {
        this.processDate = processDate;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", processDate=" + processDate +
                ", amount=" + amount +
                '}';
    }
}
