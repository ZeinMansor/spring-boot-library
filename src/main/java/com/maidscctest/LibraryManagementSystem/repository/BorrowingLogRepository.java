package com.maidscctest.LibraryManagementSystem.repository;

import com.maidscctest.LibraryManagementSystem.models.Book;
import com.maidscctest.LibraryManagementSystem.models.BorrowRecord;
import com.maidscctest.LibraryManagementSystem.models.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public interface  BorrowingLogRepository extends JpaRepository<BorrowRecord, Long> {
    Optional<BorrowRecord> findFirstRecordByBookId(Long bookId);

    Optional<BorrowRecord> findByBookIdAndPatronId(Long bookId, Long patronId);


}
