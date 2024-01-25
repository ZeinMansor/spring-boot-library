package com.maidscctest.LibraryManagementSystem.services;


import com.maidscctest.LibraryManagementSystem.dto.BookInfoDTO;
import com.maidscctest.LibraryManagementSystem.dto.ResponseDTO;
import com.maidscctest.LibraryManagementSystem.models.Book;
import com.maidscctest.LibraryManagementSystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BooksService {

    @Autowired
    private BookRepository repository;


    public ResponseDTO getAllBooks() {
        try {
            List<Book> bookList = this.repository.findAll();
            return new ResponseDTO(HttpStatus.ACCEPTED, "Success", this.mapBookDto(bookList));
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());
        }
    }

    @Cacheable("books")
    public ResponseDTO getBookById(Long id) {
        try {
            Book book = this.repository.findById(id).get();
            return new ResponseDTO(HttpStatus.ACCEPTED, "Success", this.mapBookDto(book));
        } catch (NoSuchElementException e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, String.format("Book with Id %d does not exist...!", id) );
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());
        }
    }


    public ResponseDTO addNewBook(BookInfoDTO book) {
        try {
            Book newBook = new Book(
                    book.getTitle(),
                    book.getDescription(),
                    book.getAuthor(),
                    book.getPublishDate()
            );

            this.repository.save(newBook);
            return new ResponseDTO(HttpStatus.ACCEPTED, "Success", this.mapBookDto(newBook));

        } catch (DuplicateKeyException e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, "Error, This book already exists " + e.getMessage());
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());
        }
    }

    @CacheEvict(value = "books", key = "#id")
    public ResponseDTO updateBookInfo(BookInfoDTO bookInfoDTO, Long id) {
        try {
            Book book = this.repository.findById(id).get();
            book.setTitle(bookInfoDTO.getTitle());
            book.setAuthor(bookInfoDTO.getAuthor());
            book.setDescription(bookInfoDTO.getAuthor());
            book.setPublishDate(bookInfoDTO.getPublishDate());

            this.repository.save(book);
            return new ResponseDTO(HttpStatus.ACCEPTED, "Success, Updated Successfully", book);

        } catch (NoSuchElementException e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, String.format("Book with Id %d does not exist...!", id) );
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage() );
        }
    }

    public ResponseDTO deleteBook(Long id) {
        try {
            this.repository.deleteById(id);
            return new ResponseDTO(HttpStatus.ACCEPTED, "Success, Deleted Successfully");

        } catch (NoSuchElementException e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, String.format("Book with Id %d does not exist...!", id) );
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage() );

        }
    }


    private BookInfoDTO mapBookDto(Book book) {
        return new BookInfoDTO(book);
    }

    private List<BookInfoDTO> mapBookDto(List<Book> bookList) {
        List<BookInfoDTO> bookInfoDTOList = new ArrayList<>();
        bookList.forEach(book -> {
            bookInfoDTOList.add(new BookInfoDTO(book));
        });
        return bookInfoDTOList;
    }

}