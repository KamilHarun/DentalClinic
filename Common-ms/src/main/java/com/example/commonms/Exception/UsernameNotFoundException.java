package com.example.commonms.Exception;

import lombok.Getter;

@Getter
public class UsernameNotFoundException extends RuntimeException {

    private final ErrorMessage errorMessage;
    public UsernameNotFoundException(ErrorMessage message,Object...args) {
        super(String.format(message.getMessage(),args));
        this.errorMessage = message;
    }
}
