package com.example.dentistms.Service;

import com.example.dentistms.Dto.Request.DentistRequestDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface DentistService {
    Long create(@Valid DentistRequestDto dentist);

}
