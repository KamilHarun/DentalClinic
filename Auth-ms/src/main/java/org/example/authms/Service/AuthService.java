package org.example.authms.Service;

import org.example.authms.Dto.LoginRequest;
import org.example.authms.Dto.RegisterRequestDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public interface AuthService {

    <T> Mono<Void> sendOtp(String email);

    Mono<Object> login(LoginRequest request);

    Mono<Object> register(RegisterRequestDto request);
}
