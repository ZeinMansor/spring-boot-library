package com.maidscctest.LibraryManagementSystem.dto;

import com.maidscctest.LibraryManagementSystem.models.Patron;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Data
@Setter
@RequiredArgsConstructor
public class PatronInfoDTO {
    private Long id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;


    public PatronInfoDTO(Patron patron) {
        this.id = patron.getId();
        this.email = patron.getEmail();
        this.firstName = patron.getFirstName();
        this.lastName = patron.getLastName();
    }

}
