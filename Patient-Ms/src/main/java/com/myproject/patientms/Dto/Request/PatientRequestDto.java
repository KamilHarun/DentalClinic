package com.myproject.patientms.Dto.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientRequestDto implements Serializable {
    String name;
    Long age;
    String surname;
    String phoneNumber;
    String email;
    String gender;
    String status;
    Long dentistId;

    LocalDateTime createdDate;
}
