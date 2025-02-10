package com.example.commonms.Exception;

import lombok.Getter;

@Getter
public class AppointmentNotFoundException extends RuntimeException{

    private final ErrorMessage errorMessage;
    public AppointmentNotFoundException(ErrorMessage errorMessage , Object...args) {
        super(String.format(errorMessage.getMessage() , args));
        this.errorMessage = errorMessage;
    }
}
