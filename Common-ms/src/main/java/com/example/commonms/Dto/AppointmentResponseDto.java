package com.example.commonms.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentResponseDto implements Serializable {

    Long id;

    Long patientId;

    Long dentistId;

    LocalDateTime appointmentDate;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    String description;

    Integer durationInMinutes;
    String notes;
}