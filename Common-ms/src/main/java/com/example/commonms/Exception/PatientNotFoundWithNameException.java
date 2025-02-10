package com.example.commonms.Exception;

import lombok.Getter;

@Getter
public class PatientNotFoundWithNameException extends RuntimeException{
    private final ErrorMessage errorMessage;
    public PatientNotFoundWithNameException(ErrorMessage errorMessage , Object ...args) {
        super(String.format(errorMessage.getMessage() , args));
        this.errorMessage = errorMessage;
    }
}
