package com.maidscctest.LibraryManagementSystem.repository;

import com.maidscctest.LibraryManagementSystem.models.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long> {
}
