package com.maidscctest.LibraryManagementSystem.controllers;


import com.maidscctest.LibraryManagementSystem.dto.ResponseDTO;
import com.maidscctest.LibraryManagementSystem.services.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api")
public class BorrowingController {

    @Autowired
    BorrowingService borrowingService;
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseDTO lendBookToUser(@PathVariable Long bookId, @PathVariable Long patronId) {
        return this.borrowingService.lendBookToPatron(bookId, patronId);
    }



    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseDTO returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        return this.borrowingService.returnBookToLibrary(bookId, patronId);
    }

}
