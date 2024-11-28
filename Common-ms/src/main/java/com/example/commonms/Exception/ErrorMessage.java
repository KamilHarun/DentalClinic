package com.example.commonms.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    PATIENT_ALREADY_EXIST_EXCEPTION ("Patient already exist"),
    PATIENT_NOT_FOUND_EXCEPTION ("Patient not found"),
    PATIENT_NOT_FOUND_WITH_NAME_EXCEPTION("Patient not found with given name"),
    APPOINTMENT_NOT_FOUND_EXCEPTION("Appointment not found"),
    DENTIST_ALREADY_EXIST_EXCEPTION ("Dental already exist");


    private final String message;
    public String format(Object... args) {
        return String.format(this.message, args);
    }
}

