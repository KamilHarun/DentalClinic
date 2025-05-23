package com.example.dentistms.Repository;

import com.example.dentistms.Dto.Response.DentistResponseDto;
import com.example.dentistms.Model.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DentistRepo extends JpaRepository<Dentist, Long> {
    Optional<Dentist> findByEmail(String email);


    List<Dentist> findByFirstNameContainingIgnoreCase(String name);
}
