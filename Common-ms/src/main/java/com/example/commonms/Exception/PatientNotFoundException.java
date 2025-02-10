package com.example.commonms.Exception;

import lombok.Getter;

@Getter
public class PatientNotFoundException extends RuntimeException{
    private final ErrorMessage errorMessage;
    public PatientNotFoundException(ErrorMessage errorMessage , Object ...args) {
        super(String.format(errorMessage.getMessage() , args));
        this.errorMessage = errorMessage;
    }
}
