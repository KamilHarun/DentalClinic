package com.example.dentistms.Config;

import com.example.dentistms.Dto.Request.DentistRequestDto;
import com.example.dentistms.Model.Dentist;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DentistMapper {

    DentistRequestDto requestToDentist(Dentist dentist);
    Dentist dentistDtoToDentist(DentistRequestDto dentistRequestDto);


}
