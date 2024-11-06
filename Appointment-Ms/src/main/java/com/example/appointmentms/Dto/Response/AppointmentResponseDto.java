package com.example.appointmentms.Dto.Response;

import com.example.appointmentms.Enums.AppointmentStatus;
import com.example.appointmentms.Enums.TreatmentType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
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

    AppointmentStatus status;

    String description;

    TreatmentType treatmentType;

    Integer durationInMinutes;
    String notes;
}
