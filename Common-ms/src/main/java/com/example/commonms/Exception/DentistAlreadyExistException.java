package com.example.commonms.Exception;

import lombok.Getter;

@Getter
public class DentistAlreadyExistException extends RuntimeException{
    private final ErrorMessage errorMessage;

    public DentistAlreadyExistException(ErrorMessage errorMessage, Object...args) {
        super(String.format(errorMessage.getMessage(), args));
        this.errorMessage = errorMessage;
    }
}
