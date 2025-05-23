package com.example.commonms.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientResponseDto implements Serializable {
    String name;
    Long age;
    String surname;
    String phoneNumber;
    String email;
    String gender;
    String status;
    LocalDateTime createdDate;

}