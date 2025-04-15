package com.myproject.patientms.Repository;

import com.myproject.patientms.Dto.Response.PatientResponseDto;
import com.myproject.patientms.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepo extends JpaRepository<Patient , Long> {
    Optional<Patient> findByEmail(String email);

    @Query()
    List<Patient> findByName(String name);

//    List<Appointment> findByPatientId(Long id);
}
