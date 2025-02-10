package com.example.commonms.Exception;

import lombok.Getter;

@Getter
public class DentistUnavailableException extends RuntimeException{

    private final ErrorMessage errorMessage;

    public DentistUnavailableException(ErrorMessage errorMessage, Object... args) {
        super(String.format(errorMessage.getMessage(), args));
        this.errorMessage = errorMessage;
    }

}
