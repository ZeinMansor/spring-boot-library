package com.maidscctest.LibraryManagementSystem.controllers;


import com.maidscctest.LibraryManagementSystem.dto.BookInfoDTO;
import com.maidscctest.LibraryManagementSystem.dto.ResponseDTO;
import com.maidscctest.LibraryManagementSystem.interfaces.LogExecutionTime;
import com.maidscctest.LibraryManagementSystem.services.BooksService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BooksService booksService;
    @GetMapping

    @LogExecutionTime(additionalMessage = "Get All Books Endpoint Execution Time")
    public ResponseDTO getAllBooks() {
        return this.booksService.getAllBooks();
    }

    @GetMapping("/{id}")
    @LogExecutionTime(additionalMessage = "Get Book By Id Endpoint Execution Time")
    public ResponseDTO getBook(@PathVariable("id") long id) {
        return this.booksService.getBookById(id);
    }


    @PostMapping
    @LogExecutionTime(additionalMessage = "Get Add New Book Endpoint Execution Time")
    public ResponseDTO addNewBook(@Valid @RequestBody BookInfoDTO book) {
        return this.booksService.addNewBook(book);
    }


    @PutMapping("/{id}")
    @LogExecutionTime(additionalMessage = "Get Update Book Info Endpoint Execution Time")
    public ResponseDTO updateBookInfo(@RequestBody BookInfoDTO book, @PathVariable Long id) {
        return this.booksService.updateBookInfo(book, id);
    }

    @DeleteMapping("/{id}")
    @LogExecutionTime(additionalMessage = "Get Delete Book Endpoint Execution Time")
    public ResponseDTO deleteBook(@PathVariable Long id) {
        return this.booksService.deleteBook(id);
    }

}
