package com.example.appointmentms.Service.impl;

import com.example.appointmentms.Dto.Request.AppointmentRequestDto;
import com.example.appointmentms.Dto.Response.AppointmentResponseDto;
import com.example.appointmentms.Feign.PatientFeign;
import com.example.appointmentms.Mapper.AppointmentMapper;
import com.example.appointmentms.Model.Appointment;
import com.example.appointmentms.Repository.AppointmentRepo;
import com.example.appointmentms.Service.AppointmentService;
import com.example.commonms.Dto.PatientResponseDto;
import com.example.commonms.Exception.AppointmentNotFoundException;
import com.example.commonms.Exception.ErrorMessage;
import com.example.commonms.Exception.PatientNotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.commonms.Exception.ErrorMessage.APPOINTMENT_NOT_FOUND_EXCEPTION;
import static com.example.commonms.Exception.ErrorMessage.PATIENT_NOT_FOUND_EXCEPTION;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepo appointmentRepo;
    private final AppointmentMapper mapper;
    private final PatientFeign patientFeign;

    @Override
    public Long create(AppointmentRequestDto appointmentRequestDto) {

        PatientResponseDto patient = patientFeign.findById(appointmentRequestDto.getPatientId());
        if (patient == null || appointmentRequestDto.getPatientId() == null) {
            throw new RuntimeException("Patient not found");
        }
        Appointment appointment = mapper.requestDtoToAppointment(appointmentRequestDto);
        appointmentRepo.save(appointment);

        return appointment.getId();
    }

    @Override
    public AppointmentResponseDto findById(Long id) {
        Appointment appointment = appointmentRepo.findById(id).orElseThrow(() ->
                new AppointmentNotFoundException(APPOINTMENT_NOT_FOUND_EXCEPTION));
        return mapper.appointmentToResponseDto(appointment);
    }

    @Override
    public Page<AppointmentResponseDto> getAll(Pageable pageable) {
        Page<Appointment> all = appointmentRepo.findAll(pageable);
        if (all.isEmpty()) {
            throw new AppointmentNotFoundException(APPOINTMENT_NOT_FOUND_EXCEPTION);
        }
        List<AppointmentResponseDto> appointmentRequestDtos = all.stream()
                .map(appointment -> AppointmentResponseDto.builder()
                        .appointmentDate(appointment.getAppointmentDate())
                        .notes(appointment.getNotes())
                        .status(appointment.getStatus())
                        .treatmentType(appointment.getTreatmentType())
                        .durationInMinutes(appointment.getDurationInMinutes())
                        .description(appointment.getDescription())
                        .dentistId(appointment.getDentistId())
//                        .patientId(appointment.getPatientId())
                        .createdAt(appointment.getCreatedAt())
                        .updatedAt(appointment.getUpdatedAt())
                        .id(appointment.getId())
                        .build())
                .collect(Collectors.toList());

        return new PageImpl<>(appointmentRequestDtos, pageable, all.getTotalElements());

    }

    @Override
    @Transactional
    public AppointmentResponseDto update(AppointmentRequestDto appointmentRequestDto, Long id) {
        log.info("Starting the update process for appointment with ID: {}", id);

        Appointment existingAppointment = appointmentRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Appointment with ID {} not found", id);
                    return new AppointmentNotFoundException(APPOINTMENT_NOT_FOUND_EXCEPTION);
                });

        try {
            PatientResponseDto byId = patientFeign.findById(appointmentRequestDto.getId());
        } catch (FeignException.NotFound ex) {
            throw new PatientNotFoundException(PATIENT_NOT_FOUND_EXCEPTION);
        }

        log.debug("Existing appointment retrieved: {}", existingAppointment);

//        existingAppointment.setPatientId(appointmentRequestDto.getPatientId());
        existingAppointment.setDentistId(appointmentRequestDto.getDentistId());
        existingAppointment.setAppointmentDate(appointmentRequestDto.getAppointmentDate());
        existingAppointment.setStatus(appointmentRequestDto.getStatus());
        existingAppointment.setDescription(appointmentRequestDto.getDescription());
        existingAppointment.setTreatmentType(appointmentRequestDto.getTreatmentType());
        existingAppointment.setDurationInMinutes(appointmentRequestDto.getDurationInMinutes());
        existingAppointment.setNotes(appointmentRequestDto.getNotes());
        existingAppointment.setUpdatedAt(LocalDateTime.now());

        log.info("Updating appointment with new values: {}", appointmentRequestDto);

        Appointment updatedAppointment = appointmentRepo.save(existingAppointment);

        log.info("Appointment with ID {} successfully updated", id);

        return mapper.appointmentToResponseDto(updatedAppointment);
    }

    @Override
    public void delete(Long appointmentId) {
        log.info("Deleting patient with ID: {}", appointmentId);
        Appointment appointment = appointmentRepo.findById(appointmentId).orElseThrow(
                () ->
        new AppointmentNotFoundException(APPOINTMENT_NOT_FOUND_EXCEPTION));
        appointmentRepo.delete(appointment);
        log.info("Appointment with ID: {} deleted successfully.", appointmentId);
    }

    @Override
    public Page<AppointmentResponseDto> getAppointmentByPatient(Long patientId, Pageable pageable) {
        validatePatient(patientId);

        Page<Appointment> appointments = appointmentRepo.findByPatientId(patientId, pageable);

        return appointments.map(mapper::appointmentToResponseDto);
    }

    @Override
    public Page<AppointmentResponseDto> getAppointmentByHistory(String date, Pageable pageable) {
        log.info("Fetching appointments for date: {}", date);

        LocalDate parsedDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            parsedDate = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            log.error("Invalid date format: {}", date);
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd.");
        }

        Page<Appointment> appointments = appointmentRepo.findByAppointmentDate(parsedDate, pageable);

        if (appointments.isEmpty()) {
            log.warn("No appointments found for date: {}", parsedDate);
        }

        return appointments.map(mapper::appointmentToResponseDto);
    }


    public void validatePatient(Long patientId) {
        try {
            PatientResponseDto byId = patientFeign.findById(patientId);
        } catch (FeignException.NotFound e) {
            log.error("Patient not found with ID: {}", patientId);
            throw new PatientNotFoundException(PATIENT_NOT_FOUND_EXCEPTION);
        }
    }


}
