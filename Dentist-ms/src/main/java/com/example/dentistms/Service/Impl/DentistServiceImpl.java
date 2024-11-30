package com.example.dentistms.Service.Impl;

import com.example.commonms.Dto.PatientResponseDto;
import com.example.commonms.Exception.DentistAlreadyExistException;
import com.example.commonms.Exception.DentistNotFoundException;
import com.example.commonms.Exception.ErrorMessage;
import com.example.dentistms.Config.DentistMapper;
import com.example.dentistms.Dto.Request.DentistRequestDto;
import com.example.dentistms.Dto.Response.DentistResponseDto;
import com.example.dentistms.Feign.PatientFeing;
import com.example.dentistms.Model.Dentist;
import com.example.dentistms.Repository.DentistRepo;
import com.example.dentistms.Service.DentistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DentistServiceImpl implements DentistService {

    private final DentistRepo dentistRepository;
    private final DentistMapper mapper;
    private final PatientFeing patientFeing;

    @Override
    public Long create(DentistRequestDto dentist) {
        log.info("Create dentist");
        Optional<Dentist> existingDentist = dentistRepository.findByEmail(dentist.getEmail());
        if (existingDentist.isPresent()) {
            log.error("Dentist already exists");
            throw new DentistAlreadyExistException(ErrorMessage.DENTIST_ALREADY_EXIST_EXCEPTION);
        }

        Dentist toDentist = mapper.dentistDtoToDentist(dentist);
        dentistRepository.save(toDentist);
        return toDentist.getId();
    }

    @Override
    @Cacheable(cacheNames = "dentists" , key = "#id")
    public DentistResponseDto findById(Long id) {
        log.debug("Request to get Dentist : {}", id);
        if (id == null) {
            log.error("Request to get Dentist id is null");
            throw  new IllegalArgumentException("ID cannot be null");
    }
        log.info("Request to get Dentist : {}", id);
        return dentistRepository.findById(id)
                .map(mapper::dentistToResponseDto)
                .orElseThrow(()->
                        new DentistNotFoundException(ErrorMessage.DENTIST_NOT_FOUND_EXCEPTION));
    }

    @Override
    @Cacheable(cacheNames = "patients",
            key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort.toString()")    public Page<DentistResponseDto> findAll(Pageable pageable) {
        log.debug("Request to get all Dentists");
        Page<Dentist> all = dentistRepository.findAll(pageable);
        if (all.isEmpty()){
            log.error("Request to get all Dentists is empty");
            throw new DentistNotFoundException(ErrorMessage.DENTIST_NOT_FOUND_EXCEPTION);
        }
        log.info("Request to get all Dentists : {}", all);
        return all.map(mapper::dentistToResponseDto);

    }

    @Override
    public DentistResponseDto update(Long id, DentistRequestDto updatedDentist) {
        log.debug("Request to get Dentist : {}", id);
        Dentist existingDentist = dentistRepository.findById(id)
                .orElseThrow(() -> new DentistNotFoundException(ErrorMessage.DENTIST_NOT_FOUND_EXCEPTION));
        mapper.updateDentistFromDto(updatedDentist, existingDentist);
        dentistRepository.save(existingDentist);
        return mapper.dentistToResponseDto(existingDentist);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dentist : {}", id);
        Dentist dentist = dentistRepository.findById(id)
                .orElseThrow(() -> new DentistNotFoundException(ErrorMessage.DENTIST_NOT_FOUND_EXCEPTION));
        dentistRepository.delete(dentist);

    }

    @Override
    public List<DentistResponseDto> findDentistsPatient(Long dentistId) {

        List<PatientResponseDto> patientsByDentist = patientFeing.getPatientsByDentist(dentistId);

        return mapper.toDentistResponseDtoList(patientsByDentist);

    }
}