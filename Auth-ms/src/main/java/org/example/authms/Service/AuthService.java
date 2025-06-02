package org.example.authms.Service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public interface AuthService {

    <T> Mono<Void> sendOtp(String email);

    Mono<Object> login(String email, String password);

    Mono<Object> register(String email, String password, String otp);
}
