package com.maidscctest.LibraryManagementSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long Id;

    @Column(name = "patron_id")
    public Long patronId;

    @Column(name = "book_id")
    public Long bookId;

    @Column(name = "borrowed_at")
    @NotNull
    public Date borrowedAt;

    @Column(name = "returned_at")
    public Date returnedAt;

    public BorrowRecord(Long patronId, Long bookId, Date borrowedAt) {
        this.patronId = patronId;
        this.bookId = bookId;
        this.borrowedAt = borrowedAt;
    }
}
