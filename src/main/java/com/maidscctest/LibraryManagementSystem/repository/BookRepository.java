package com.maidscctest.LibraryManagementSystem.repository;

import com.maidscctest.LibraryManagementSystem.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
