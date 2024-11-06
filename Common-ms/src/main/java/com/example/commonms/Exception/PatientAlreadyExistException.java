package com.example.commonms.Exception;

public class PatientAlreadyExistException extends RuntimeException{
    public PatientAlreadyExistException(ErrorMessage errorMessage , Object ...args) {
        super(String.format(errorMessage.getMessage() , args));
    }
}
