package com.maidscctest.LibraryManagementSystem.controllers;


import com.maidscctest.LibraryManagementSystem.dto.PatronInfoDTO;
import com.maidscctest.LibraryManagementSystem.dto.ResponseDTO;
import com.maidscctest.LibraryManagementSystem.models.Patron;
import com.maidscctest.LibraryManagementSystem.services.PatronsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {


    @Autowired
    private PatronsService patronsService;
    @GetMapping
    public ResponseDTO getAllPatrons() {
        return this.patronsService.getAllPatrons();
    }

    @GetMapping("/{id}")
    public ResponseDTO getPatronInfo(@PathVariable Long id) {
        return this.patronsService.getPatronById(id);
    }

    @PostMapping
    public ResponseDTO addNewPatron(@RequestBody PatronInfoDTO patron) {
        return this.patronsService.addNewPatron(patron);
    }


    @PutMapping("/{id}")
    public ResponseDTO updatePatronInfo(@RequestBody PatronInfoDTO patron, @PathVariable Long id) {
        return this.patronsService.updatePatronInfo(patron, id);
    }

}
