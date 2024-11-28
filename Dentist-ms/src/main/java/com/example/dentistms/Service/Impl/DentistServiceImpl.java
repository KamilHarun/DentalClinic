package com.example.dentistms.Service.Impl;

import com.example.commonms.Exception.DentistAlreadyExistException;
import com.example.commonms.Exception.ErrorMessage;
import com.example.dentistms.Config.DentistMapper;
import com.example.dentistms.Dto.Request.DentistRequestDto;
import com.example.dentistms.Model.Dentist;
import com.example.dentistms.Repository.DentistRepo;
import com.example.dentistms.Service.DentistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DentistServiceImpl implements DentistService {

    private final DentistRepo dentistRepository;
    private final DentistMapper mapper;

    @Override
    public Long create(DentistRequestDto dentist) {
        Optional<Dentist> existingDentist = dentistRepository.findByEmail(dentist.getEmail());
        if (existingDentist.isPresent()) {
            throw new DentistAlreadyExistException(ErrorMessage.DENTIST_ALREADY_EXIST_EXCEPTION);
        }
        Dentist toDentist = mapper.dentistDtoToDentist(dentist);
        dentistRepository.save(toDentist);
        return toDentist.getId();
    }

}