package com.example.dentistms.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DentistRequestDto {
    Long id;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;

}
