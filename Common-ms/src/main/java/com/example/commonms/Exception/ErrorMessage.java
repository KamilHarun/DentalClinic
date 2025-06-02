package com.example.commonms.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    PATIENT_ALREADY_EXIST_EXCEPTION ("Patient already exist %s"),
    PATIENT_NOT_FOUND_EXCEPTION ("Patient not found %s"),
    PATIENT_NOT_FOUND_WITH_NAME_EXCEPTION("Patient not found with given name %s" ),
    APPOINTMENT_NOT_FOUND_EXCEPTION("Appointment not found %s "),
    DENTIST_ALREADY_EXIST_EXCEPTION ("Dental already exist %s "),
    DENTIST_NOT_FOUND_EXCEPTION ("Dental not found %s "),
    DENTIST_UNAVAILABLE_EXCEPTION ("Dentist is not available for the selected date and time %s "),
    INFORMATION_NOT_FOUND_EXCEPTION ("Information not found %s "),
    EMAIL_ALREADY_EXIST_EXCEPTION ("Email already exist %s "),
    USERNAME_NOT_FOUND_EXCEPTION ("Username not found %s "),
    OKTA_REGISTRATION_EXCEPTION ("Okta registration failed %s ");


    private final String message;
    public String format(Object... args) {
        return String.format(this.message, args);
    }
}

