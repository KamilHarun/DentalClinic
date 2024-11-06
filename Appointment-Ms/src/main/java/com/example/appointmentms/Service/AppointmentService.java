package com.example.appointmentms.Service;

import com.example.appointmentms.Dto.Request.AppointmentRequestDto;
import com.example.appointmentms.Dto.Response.AppointmentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

public interface AppointmentService {
    Long create(AppointmentRequestDto appointmentRequestDto);

    AppointmentResponseDto findById(Long id);

    Page<AppointmentResponseDto> getAll(Pageable pageable);

    AppointmentResponseDto update(AppointmentRequestDto appointmentRequestDto, Long id);

    void delete(Long appointmentId);

    Page<AppointmentResponseDto> getAppointmentByPatient(Long patientId, Pageable pageable);

    Page<AppointmentResponseDto> getAppointmentByHistory(String date, Pageable pageable);
}
