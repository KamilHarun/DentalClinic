package org.example.authms.Controller;

import lombok.RequiredArgsConstructor;
import org.example.authms.Dto.*;
import org.example.authms.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody RegisterRequestDto request) {
        return authService.register(request)
                .map(response -> ResponseEntity.ok(response.toString()));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody LoginRequest request) {
        return authService.login(request)
                .map(response -> ResponseEntity.ok((String) response));
    }

    @PostMapping("/send-otp")
    public Mono<ResponseEntity<String>> sendOtp(@RequestBody OtpRequest request) {
        return authService.sendOtp(request.getEmail())
                .thenReturn(ResponseEntity.ok("OTP sent successfully to " + request.getEmail()))
                .onErrorResume(ex -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to send OTP: " + ex.getMessage())));
    }

}