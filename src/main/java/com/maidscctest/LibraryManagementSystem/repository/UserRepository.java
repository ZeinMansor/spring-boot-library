package com.maidscctest.LibraryManagementSystem.repository;

import com.maidscctest.LibraryManagementSystem.models.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    public User findUserByEmail(String email) {
        return new User(email,"123456", "Zein");

    }
}
