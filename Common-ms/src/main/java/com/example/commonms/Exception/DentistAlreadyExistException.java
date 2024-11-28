package com.example.commonms.Exception;

public class DentistAlreadyExistException extends RuntimeException{
    public DentistAlreadyExistException(ErrorMessage errorMessage, Object...args) {
        super(String.format(errorMessage.getMessage(), args));
    }
}
