package com.maidscctest.LibraryManagementSystem.services;


import com.maidscctest.LibraryManagementSystem.dto.PatronInfoDTO;
import com.maidscctest.LibraryManagementSystem.dto.ResponseDTO;
import com.maidscctest.LibraryManagementSystem.models.Patron;
import com.maidscctest.LibraryManagementSystem.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PatronsService {

    @Autowired
    private PatronRepository patronRepository;


    public ResponseDTO getAllPatrons() {
        try {
            List<Patron> patronList = this.patronRepository.findAll();

            return new ResponseDTO(
                    HttpStatus.ACCEPTED,
                    "Success",
                    this.mapPatronToDTO(patronList)
                    );
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());

        }
    }

    @Cacheable("patrons")
    public ResponseDTO getPatronById(Long id) {
        try {
            Patron patron = this.patronRepository.findById(id).get();
            return new ResponseDTO(
                    HttpStatus.ACCEPTED,
                    "Success",
                    this.mapPatronToDTO(patron)
            );
        } catch (NoSuchElementException e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, String.format("Patron with Id %d does not exist...!", id) );
        }
        catch (Exception e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());
        }
    }

    public ResponseDTO addNewPatron(PatronInfoDTO patron) {
        try {

            Patron newPatron = new Patron(
                    patron.getEmail(),
                    patron.getPassword(),
                    patron.getFirstName(),
                    patron.getLastName()
            );
            PatronInfoDTO patronInfoDTO = this.mapPatronToDTO(this.patronRepository.save(newPatron));
            return new ResponseDTO(HttpStatus.ACCEPTED, "Added Successfully", patronInfoDTO);
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @CacheEvict(value = "patrons", key = "#id")
    public ResponseDTO updatePatronInfo(PatronInfoDTO patronInfo, Long id) {
        try {
            Patron storedPatron =  this.patronRepository.findById(id).get();

            storedPatron.setEmail(patronInfo.getEmail());
            storedPatron.setPassword(patronInfo.getPassword());
            storedPatron.setFirstName(patronInfo.getFirstName());
            storedPatron.setLastName(patronInfo.getLastName());

            this.patronRepository.save(storedPatron);
            PatronInfoDTO patronInfoDTO = this.mapPatronToDTO(storedPatron);
            return new ResponseDTO(HttpStatus.ACCEPTED, "Added Successfully", patronInfoDTO);

        } catch (NoSuchElementException e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, String.format("Patron with Id %d does not exist...!", id) );
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public ResponseDTO deleteBook(Long id) {
        try {
            this.patronRepository.deleteById(id);
            return new ResponseDTO(HttpStatus.ACCEPTED, "Success, Deleted Successfully");

        } catch (NoSuchElementException e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, String.format("Patron with Id %d does not exist...!", id) );
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage() );

        }
    }
    private PatronInfoDTO mapPatronToDTO(Patron patron) {
        return new PatronInfoDTO(patron);
    }

    private List<PatronInfoDTO> mapPatronToDTO(List<Patron> patronList) {
        List<PatronInfoDTO> patronInfoDTOS = new ArrayList<>();
        patronList.forEach(patron -> {
            patronInfoDTOS.add(this.mapPatronToDTO(patron));
        });
        return  patronInfoDTOS;
    }


}
