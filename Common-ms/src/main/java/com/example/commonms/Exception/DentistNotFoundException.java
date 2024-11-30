package com.example.commonms.Exception;

public class DentistNotFoundException extends RuntimeException{
    public DentistNotFoundException(ErrorMessage errorMessage , Object...args) {
        super(String.format(errorMessage.getMessage(),args));
    }
}
