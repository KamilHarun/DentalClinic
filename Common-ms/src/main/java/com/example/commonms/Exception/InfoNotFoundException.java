package com.example.commonms.Exception;

import lombok.Getter;

@Getter
public class InfoNotFoundException extends RuntimeException{

    private final ErrorMessage errorMessage;

    public InfoNotFoundException(ErrorMessage errorMessage , Object...args) {
        super(String.format(errorMessage.getMessage(),args));
        this.errorMessage = errorMessage;
    }
}
