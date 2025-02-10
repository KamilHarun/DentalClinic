package com.example.commonms.Exception;

import lombok.Getter;

@Getter
public class DentistNotFoundException extends RuntimeException{
    private final ErrorMessage errorMessage;

    public DentistNotFoundException(ErrorMessage errorMessage , Object...args) {
        super(String.format(errorMessage.getMessage(),args));
        this.errorMessage = errorMessage;
    }
}
