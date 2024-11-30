package com.example.dentistms.Config;

import com.example.commonms.Dto.PatientResponseDto;
import com.example.dentistms.Dto.Request.DentistRequestDto;
import com.example.dentistms.Dto.Response.DentistResponseDto;
import com.example.dentistms.Model.Dentist;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DentistMapper {

    DentistRequestDto requestToDentist(Dentist dentist);
    Dentist dentistDtoToDentist(DentistRequestDto dentistRequestDto);
    DentistResponseDto dentistToResponseDto(Dentist dentist);


    void updateDentistFromDto(DentistRequestDto updatedDentist, @MappingTarget Dentist existingDentist);

    DentistResponseDto toDentistResponseDto(PatientResponseDto patientResponseDto);

    List<DentistResponseDto> toDentistResponseDtoList(List<PatientResponseDto> patientResponseDtoList);
}

