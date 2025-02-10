package com.example.commonms.Exception;

import lombok.Getter;

@Getter
public class PatientAlreadyExistException extends RuntimeException{
    private final ErrorMessage errorMessage;
    public PatientAlreadyExistException(ErrorMessage errorMessage , Object ...args) {
        super(String.format(errorMessage.getMessage() , args));
        this.errorMessage = errorMessage;
    }
}
