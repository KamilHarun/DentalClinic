package com.myproject.patientms.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Patient extends Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long patientId;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
     String name;

    @NotNull(message = "Surname cannot be null")
    @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters")
     String surname;

    @NotNull(message = "Age cannot be null")
    @Min(value = 0, message = "Age must be a positive number")
    @Max(value = 150, message = "Age must be less than or equal to 150")
     Long age;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
     String email;

    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Phone number must be valid and contain 10-15 digits")
     String phoneNumber;

    @NotNull(message = "Gender cannot be null")
    String gender;

    @NotNull(message = "Status cannot be null")
    String status;

    @NotNull(message = "Dentist cannot be null")
    Long dentistId;


}