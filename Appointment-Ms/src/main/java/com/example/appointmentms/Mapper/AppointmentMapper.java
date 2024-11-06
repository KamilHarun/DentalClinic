package com.example.appointmentms.Mapper;

import com.example.appointmentms.Dto.Request.AppointmentRequestDto;
import com.example.appointmentms.Dto.Response.AppointmentResponseDto;
import com.example.appointmentms.Model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper( componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppointmentMapper {

    AppointmentRequestDto appointmentToRequestDto(Appointment appointment);
    AppointmentResponseDto appointmentToResponseDto(Appointment appointment);

    Appointment requestDtoToAppointment(AppointmentRequestDto appointmentRequestDto);

    Appointment responseDtoToAppointment(AppointmentResponseDto appointmentResponseDto);


}
