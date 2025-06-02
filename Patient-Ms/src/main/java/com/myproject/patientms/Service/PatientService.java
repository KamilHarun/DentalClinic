package com.myproject.patientms.Service;

import com.myproject.patientms.Dto.Request.PatientRequestDto;
import com.myproject.patientms.Dto.Response.PatientResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

public interface PatientService {
    Long create(PatientRequestDto patientRequestDto);

    PatientResponseDto findById(Long id);

    Page<PatientResponseDto> getAll(Pageable pageable);

    PatientResponseDto update(Long id, PatientRequestDto patientRequestDto);

    void delete(Long id);

    List<PatientResponseDto> findByName(PatientRequestDto patientRequestDto);

    Page<PatientResponseDto> getPatientHistory(Long id , Pageable pageable);

    Map<String, Long> getPatientStatisticsByAgeGroup();

    PatientResponseDto updateContact(String phone, String email, Long id);

    PatientResponseDto updatePatientStatus(Long id, PatientRequestDto patientRequestDto);

    PatientResponseDto getPatientByEmail(String email);

}
