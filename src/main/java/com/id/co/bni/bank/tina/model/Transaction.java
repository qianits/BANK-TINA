package com.id.co.bni.bank.tina.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "TRANSACTION_BANK_TINA")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TRANSACTION_ID", nullable = false, unique = true)
    private String transactionId;

    @Column(name = "SOURCE_ACCOUNT", nullable = false, length = 9)
    private String noRekening1;

    @Column(name = "DESTINATION_ACCOUNT", nullable = false, length = 9)
    private String noRekening2;

    @Column(name = "AMOUNT", nullable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private TransactionStatus status = TransactionStatus.PENDING;

    @Column(name = "TRANSACTION_DATE", nullable = false)
    private LocalDateTime transactionDate;

    @PrePersist
    public void prePersist() {
        if (transactionDate == null) {
            transactionDate = LocalDateTime.now();
        }
    }

    public enum TransactionStatus {
        PENDING,
        SUCCESS,
        FAILED
    }

}