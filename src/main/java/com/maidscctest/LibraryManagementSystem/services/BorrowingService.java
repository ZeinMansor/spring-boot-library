package com.maidscctest.LibraryManagementSystem.services;

import com.maidscctest.LibraryManagementSystem.dto.ResponseDTO;
import com.maidscctest.LibraryManagementSystem.models.Book;
import com.maidscctest.LibraryManagementSystem.models.BorrowRecord;
import com.maidscctest.LibraryManagementSystem.models.Patron;
import com.maidscctest.LibraryManagementSystem.repository.BookRepository;
import com.maidscctest.LibraryManagementSystem.repository.BorrowingLogRepository;
import com.maidscctest.LibraryManagementSystem.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BorrowingService {

    @Autowired
    private PatronRepository patronRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BorrowingLogRepository borrowingLogRepository;

    public ResponseDTO lendBookToPatron(Long bookId, Long patronId) {
        try {
            Book book = this.bookRepository.findById(bookId).get();
            Patron patron = this.patronRepository.findById(patronId).get();

            // to check if book is already borrowed
            Optional<BorrowRecord> record = this.borrowingLogRepository.findFirstRecordByBookId(book.getId());


            if(record.isPresent() && record.get().returnedAt == null) {
                return new ResponseDTO(HttpStatus.NOT_ACCEPTABLE, String.format("Error, Book with ID %d is borrowed and not returned.", bookId));
            }

            BorrowRecord newRecord = new BorrowRecord(bookId, patronId, new Date());

            this.borrowingLogRepository.save(newRecord);
            return new ResponseDTO(HttpStatus.ACCEPTED, "Success", newRecord);

        } catch (NoSuchElementException e) {
            return new ResponseDTO(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.SERVICE_UNAVAILABLE, "Error, " + e.getMessage());
        }
    }

    public ResponseDTO returnBookToLibrary(Long bookId, Long patronId) {
        try {
            // to validate if elements exist
            Book book = this.bookRepository.findById(bookId).get();
            Patron patron = this.patronRepository.findById(patronId).get();

            // to check if book is already borrowed
            BorrowRecord record = this.borrowingLogRepository.findByBookIdAndPatronId(bookId, patronId).get();
            return new ResponseDTO(HttpStatus.ACCEPTED, "Success", record);
//            record.setReturnedAt(new Date());
//            this.borrowingLogRepository.save(record);

//            return new ResponseDTO(HttpStatus.ACCEPTED, "Success", record);

        } catch (NoSuchElementException e) {
            return new ResponseDTO(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.SERVICE_UNAVAILABLE, "Error, " + e.getMessage());
        }
    }

}
