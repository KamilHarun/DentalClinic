package com.example.dentistms.API;

import com.example.dentistms.Dto.Request.DentistRequestDto;
import com.example.dentistms.Dto.Response.DentistResponseDto;
import com.example.dentistms.Model.Dentist;
import com.example.dentistms.Service.DentistService;
import jakarta.servlet.ServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static feign.Request.HttpMethod.DELETE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/dentists")
@RequiredArgsConstructor
public class DentistController {
    private final DentistService dentistService;

    @PostMapping("/create")
    public ResponseEntity<Long> createDentist(@Valid @RequestBody DentistRequestDto dentist) {
        return new ResponseEntity<>(dentistService.create(dentist), CREATED);
    }

    @GetMapping("/findById/{id}")
    public DentistResponseDto findById(@PathVariable Long id) {
        return dentistService.findById(id);
    }

    @GetMapping("/findAll")
    public Page<DentistResponseDto> findAll(Pageable pageable) {
        return dentistService.findAll(pageable);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DentistResponseDto> updateDentist(
            @PathVariable Long id,
            @Valid @RequestBody DentistRequestDto updatedDentist) {
        return ResponseEntity.ok(dentistService.update(id, updatedDentist));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDentist(@PathVariable Long id) {
        dentistService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findDentistsPatient")
    public ResponseEntity<List<DentistResponseDto>> findDentistsPatient(@RequestParam Long dentistId) {
        return new ResponseEntity<>(dentistService.findDentistsPatient(dentistId) , OK);

    }
}