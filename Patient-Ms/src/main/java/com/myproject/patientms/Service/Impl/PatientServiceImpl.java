package com.myproject.patientms.Service.Impl;

import com.example.commonms.Dto.AppointmentResponseDto;
import com.example.commonms.Dto.DentistResponseDto;
import com.example.commonms.Exception.DentistNotFoundException;
import com.example.commonms.Exception.PatientAlreadyExistException;
import com.example.commonms.Exception.PatientNotFoundException;
import com.example.commonms.Exception.PatientNotFoundWithNameException;
import com.myproject.patientms.Config.PatientMapper;
import com.myproject.patientms.Dto.Request.PatientRequestDto;
import com.myproject.patientms.Dto.Response.PatientResponseDto;
import com.myproject.patientms.Feign.AppointmentFeign;
import com.myproject.patientms.Feign.DentistFeign;
import com.myproject.patientms.Model.Patient;
import com.myproject.patientms.Repository.PatientRepo;
import com.myproject.patientms.Service.PatientService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.commonms.Exception.ErrorMessage.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {
    private final PatientRepo patientRepo;
    private final PatientMapper mapper;
    private final AppointmentFeign appointmentFeign;
    private final DentistFeign dentistFeign;

    @Override
    @CircuitBreaker(name = "createPatient", fallbackMethod = "fallbackForCreatePatient")
    public Long create(PatientRequestDto patientRequestDto) {
        log.info("Attempting to create a new patient with email: {}", patientRequestDto.getEmail());

        Optional<Patient> patientByEmail = patientRepo.findByEmail(patientRequestDto.getEmail());
        if (patientByEmail.isPresent()) {
            log.warn("Patient creation failed. A patient with email {} already exists.", patientRequestDto.getEmail());
            throw new PatientAlreadyExistException(PATIENT_ALREADY_EXIST_EXCEPTION);
        }

        log.info("Verifying if dentist with ID {} exists.", patientRequestDto.getDentistId());

        DentistResponseDto dentist = dentistFeign.findByDentistId(patientRequestDto.getDentistId());

        log.info("Dentist response: {}", dentist);
        if (dentist == null) {
            log.warn("Patient creation failed. Dentist with ID {} does not exist.", patientRequestDto.getDentistId());
            throw new DentistNotFoundException(DENTIST_NOT_FOUND_EXCEPTION);
        }

        Patient patient = mapper.requestDtoToPatient(patientRequestDto);
        patient.setDentistId(patientRequestDto.getDentistId());
        Patient savedPatient = patientRepo.save(patient);

        log.info("Patient created successfully with ID: {}", savedPatient.getPatientId());
        return savedPatient.getPatientId();
    }


    @Override
    @Cacheable(cacheNames = "patients", key = "#id")
    public PatientResponseDto findById(Long id) {
        log.info("Fetching patient with ID: {}", id);
        Patient patient = patientRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Patient with ID {} not found.", id);
                    return new PatientNotFoundException(PATIENT_NOT_FOUND_EXCEPTION);
                });
        log.info("Patient with ID: {} found.", id);
        return mapper.patientToResponseDto(patient);
    }

    @Override
    @Cacheable(cacheNames = "getAllPatients", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort.toString()", unless = "#result.isEmpty()")
    public Page<PatientResponseDto> getAll(Pageable pageable) {
        log.info("Fetching all patients with pagination: {}", pageable);
        Page<Patient> allPatients = patientRepo.findAll(pageable);
        if (allPatients.isEmpty()) {
            log.warn("No patients found.");
            throw new PatientNotFoundException(PATIENT_NOT_FOUND_EXCEPTION);
        }

        List<PatientResponseDto> patientResponseDtos = allPatients.stream()
                .map(patient -> PatientResponseDto.builder()
                        .name(patient.getName())
                        .surname(patient.getSurname())
                        .email(patient.getEmail())
                        .phoneNumber(patient.getPhoneNumber())
                        .build())
                .toList();

        log.info("Successfully fetched {} patients.", allPatients.getTotalElements());
        return new PageImpl<>(patientResponseDtos, pageable, allPatients.getTotalElements());
    }

    @Override
    @CachePut(value = "getPatientById", key = "#id")
    public PatientResponseDto update(Long id, PatientRequestDto patientRequestDto) {
        log.info("Updating patient with ID: {}", id);
        Patient patient = patientRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Patient with ID {} not found.", id);
                    return new PatientNotFoundException(PATIENT_NOT_FOUND_EXCEPTION);
                });

        patient.setName(patientRequestDto.getName());
        patient.setSurname(patientRequestDto.getSurname());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setPhoneNumber(patientRequestDto.getPhoneNumber());

        Patient updatedPatient = patientRepo.save(patient);
        log.info("Patient with ID: {} updated successfully.", id);
        return mapper.patientToResponseDto(updatedPatient);
    }

    @Override
    @CacheEvict(cacheNames = "getPatientById", key = "#id")
    public void delete(Long id) {
        log.info("Deleting patient with ID: {}", id);
        Patient patient = patientRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Patient with ID {} not found.", id);
                    return new PatientNotFoundException(PATIENT_NOT_FOUND_EXCEPTION);
                });
        patientRepo.delete(patient);
        log.info("Patient with ID: {} deleted successfully.", id);
    }

    @Override
    @Cacheable(cacheNames = "FindByNameCache", key = "#patientRequestDto.name")
    public List<PatientResponseDto> findByName(PatientRequestDto patientRequestDto) {
        log.info("Searching Patient with name : {}", patientRequestDto.getName());
        List<Patient> patients = patientRepo.findByName(patientRequestDto.getName());
        log.warn("Patient not found with Name : {}", patientRequestDto.getName());
        if (patients.isEmpty()) {
            throw new PatientNotFoundWithNameException(PATIENT_NOT_FOUND_WITH_NAME_EXCEPTION);
        }
        return patients.stream()
                .map(mapper::patientToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = "getPatientByHistory", key = "#patientId")
    @CircuitBreaker(name = "getPatientHistory" , fallbackMethod = "fallbackForGetPatientHistory")
    public Page<PatientResponseDto> getPatientHistory(Long patientId, Pageable page) {
        log.info("Fetching patient history for patient ID: {}", patientId);

        Optional<Patient> byId = patientRepo.findById(patientId);
        if (byId.isEmpty()) {
            log.warn("Patient not found with ID: {}", patientId);
            throw new PatientNotFoundException(PATIENT_NOT_FOUND_EXCEPTION);
        }

        log.info("Patient found. Retrieving history for patient ID: {}", patientId);
        Page<AppointmentResponseDto> appointmentByPatient = appointmentFeign.getAppointmentByPatient(patientId, page);

        List<PatientResponseDto> appointmentResponseDtos = appointmentByPatient.getContent().stream()
                .sorted(Comparator.comparing(AppointmentResponseDto::getAppointmentDate))
                .map(mapper::appointmentResponseDtoToPatientResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(appointmentResponseDtos, appointmentByPatient.getPageable(), appointmentByPatient.getTotalElements());
    }

        @Override
    @Cacheable(cacheNames = "PatientStatisticByAgeGroup")
    public Map<String, Long> getPatientStatisticsByAgeGroup() {
        log.info("Generating patient statistics by age group");

        List<Patient> all = patientRepo.findAll();
        if (all.isEmpty()) {
            log.warn("No patients found for age group statistics");
            throw new PatientNotFoundException(PATIENT_NOT_FOUND_EXCEPTION);
        }

        Map<String, Long> ageGroupStatistics = all.stream()
                .collect(Collectors.groupingBy(
                        patient -> {
                            if (patient.getAge() < 18) return "Child";
                            else if (patient.getAge() < 65) return "Adult";
                            else return "Senior";
                        }, Collectors.counting()
                ));
        log.info("Patient statistics by age group generated successfully");
        return ageGroupStatistics;
    }

    @Override
    public PatientResponseDto updateContact(String phone, String email, Long id) {
        log.info("Updating contact details for patient ID: {}", id);

        Optional<Patient> patient = patientRepo.findById(id);
        if (patient.isEmpty()) {
            log.warn("Patient not found with ID: {}", id);
            throw new PatientNotFoundException(PATIENT_NOT_FOUND_EXCEPTION);
        }

        Patient patient1 = patient.get();

        String newPhone = (phone != null && !phone.isEmpty()) ? phone : patient1.getPhoneNumber();
        String newEmail = (email != null && !email.isEmpty()) ? email : patient1.getEmail();

        patient1.setPhoneNumber(newPhone);
        patient1.setEmail(newEmail);

        patientRepo.save(patient1);

        log.info("Contact details updated successfully for patient ID: {}", id);

        return mapper.patientToResponseDto(patient1);
    }

    @Override
    public PatientResponseDto updatePatientStatus(Long id, PatientRequestDto patientRequestDto) {
        log.info("Updating patient contact");
        Patient patient = patientRepo.findById(id).orElseThrow(() ->
                new PatientNotFoundException(PATIENT_NOT_FOUND_EXCEPTION)
        );
        patient.setStatus(patientRequestDto.getStatus());
        patientRepo.save(patient);

        return mapper.patientToResponseDto(patient);

    }
    public Long fallbackForCreatePatient(PatientRequestDto patientRequestDto, DentistNotFoundException dentistNotFoundException) {
        log.warn("Fallback for patient creation due to failure in dentist service.");
        throw new DentistNotFoundException(DENTIST_NOT_FOUND_EXCEPTION);
    }

    public Page<PatientResponseDto> fallbackForGetPatientHistory(Long patientId, Pageable page, Throwable throwable) {
        log.warn("Fallback for patient history due to failure in appointment service.");
        return Page.empty();

    }

}