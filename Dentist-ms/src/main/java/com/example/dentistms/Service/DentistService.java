package com.example.dentistms.Service;

import com.example.dentistms.Dto.Request.DentistRequestDto;
import com.example.dentistms.Dto.Response.DentistResponseDto;
import com.example.dentistms.Model.Dentist;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DentistService {
    Long create(@Valid DentistRequestDto dentist);

    DentistResponseDto findById(Long id);

    Page<DentistResponseDto> findAll(Pageable pageable);

    DentistResponseDto update(Long id, @Valid DentistRequestDto updatedDentist);

    void delete(Long id);

    List<DentistResponseDto> findDentistsPatient(Long dentistId);

}
