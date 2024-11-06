package com.example.commonms.Exception;

public class AppointmentNotFoundException extends RuntimeException{
    public AppointmentNotFoundException(ErrorMessage errorMessage , Object...args) {
        super(String.format(errorMessage.getMessage() , args));
    }
}
