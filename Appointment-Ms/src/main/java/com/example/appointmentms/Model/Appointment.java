package com.example.appointmentms.Model;

import com.example.appointmentms.Enums.AppointmentStatus;
import com.example.appointmentms.Enums.TreatmentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Appointment extends Audit implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "patient_id", nullable = false)
    Long patientId;

    @Column(name = "dentist_id", nullable = false)
    Long dentistId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    AppointmentStatus status;

    @Column(name = "description")
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "treatment_type")
    TreatmentType treatmentType;

    @Column(name = "duration_in_minutes")
    Integer durationInMinutes;


    @Column(name = "notes")
    String notes;


    LocalDateTime appointmentDate;

    LocalDateTime appointmentTime;

}
