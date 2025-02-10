package com.example.appointmentms.Dto.Request;

import com.example.appointmentms.Enums.AppointmentStatus;
import com.example.appointmentms.Enums.TreatmentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentRequestDto implements Serializable {

    Long id;

    Long patientId;

    Long dentistId;

    LocalDateTime appointmentDateTime;
    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    AppointmentStatus status;

    String description;

    TreatmentType treatmentType;

    Integer durationInMinutes;
    String notes;
}