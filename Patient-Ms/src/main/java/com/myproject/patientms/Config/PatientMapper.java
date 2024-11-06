package com.myproject.patientms.Config;

import com.example.appointmentms.Model.Appointment;
import com.example.commonms.Dto.AppointmentResponseDto;
import com.myproject.patientms.Dto.Request.PatientRequestDto;
import com.myproject.patientms.Dto.Response.PatientResponseDto;
import com.myproject.patientms.Model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientMapper {

    // PatientRequestDto -> Patient
    Patient requestDtoToPatient(PatientRequestDto patientRequestDto);

    // Patient ->
    PatientRequestDto patientToRequestDto(Patient patient);

    // PatientResponseDto -
    Patient responseDtoToPatient(PatientResponseDto patientResponseDto);

    PatientResponseDto patientToResponseDto(Patient patient);

    PatientResponseDto appointmentResponseDtoToPatientResponseDto(AppointmentResponseDto appointmentResponseDto);

}