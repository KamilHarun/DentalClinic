package com.example.appointmentms.Repository;

import com.example.appointmentms.Model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment , Long> {
    Page<Appointment> findByPatientId(Long patientId, Pageable pageable);

    Page<Appointment> findByAppointmentDate(LocalDate parsedDate, Pageable pageable);
}
