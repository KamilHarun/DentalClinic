package com.example.appointmentms.Repository;

import com.example.appointmentms.Model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment , Long> {
    Page<Appointment> findByPatientId(Long patientId, Pageable pageable);


//    boolean existsByDentistIdAndAppointmentDateTime(@Param("dentistId") Long dentistId, @Param("appointmentDateTime") LocalDateTime appointmentDateTime);

//    Page<Appointment> findByAppointmentDateTime(LocalDate parsedDate, Pageable pageable);


    Page<Appointment> findByCreatedAt(LocalDate parsedDate, Pageable pageable);
}
