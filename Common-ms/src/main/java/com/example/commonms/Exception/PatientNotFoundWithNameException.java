package com.example.commonms.Exception;

public class PatientNotFoundWithNameException extends RuntimeException{
    public PatientNotFoundWithNameException(ErrorMessage errorMessage , Object ...args) {
        super(String.format(errorMessage.getMessage() , args));
    }
}
