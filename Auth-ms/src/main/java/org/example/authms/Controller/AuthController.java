package org.example.authms.Controller;

import lombok.RequiredArgsConstructor;
import org.example.authms.Dto.*;
import org.example.authms.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody RegisterRequestDto request) {
        return authService.register(request.getEmail(), request.getPassword(), request.getOtp())
                .map(response -> ResponseEntity.ok(response.toString()));
    }


    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody LoginRequest request) {
        return authService.login(request.getEmail(), request.getPassword())
                .map(response -> ResponseEntity.ok((String) response));
    }

    @PostMapping("/send-login-otp")
    public Mono<ResponseEntity<String>> sendLoginOtp(@RequestBody OtpRequest request) {
        return authService.sendOtp(request.getEmail())
                .thenReturn(ResponseEntity.ok("OTP sent to email"));
    }
}