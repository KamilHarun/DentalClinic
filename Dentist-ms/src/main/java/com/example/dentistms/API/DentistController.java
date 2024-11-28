package com.example.dentistms.API;

import com.example.dentistms.Dto.Request.DentistRequestDto;
import com.example.dentistms.Model.Dentist;
import com.example.dentistms.Service.DentistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/dentists")
@RequiredArgsConstructor
public class DentistController {
    private final DentistService dentistService;

    @PostMapping("/create")
    public ResponseEntity<Long> createDentist(@Valid @RequestBody DentistRequestDto dentist) {
        return new ResponseEntity<>(dentistService.create(dentist) , CREATED);
    }

}
