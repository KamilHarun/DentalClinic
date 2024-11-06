package com.example.commonms.Exception;

public class PatientNotFoundException extends RuntimeException{
    public PatientNotFoundException(ErrorMessage errorMessage , Object ...args) {
        super(String.format(errorMessage.getMessage() , args));
    }
}
