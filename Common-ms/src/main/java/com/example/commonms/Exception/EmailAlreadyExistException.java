package com.example.commonms.Exception;

import lombok.Getter;

@Getter
public class EmailAlreadyExistException extends RuntimeException{
    public final ErrorMessage errorMessage;

    public EmailAlreadyExistException(ErrorMessage message, Object...args) {
        super(String.format(message.getMessage(),args));
        this.errorMessage = message;
    }
}
