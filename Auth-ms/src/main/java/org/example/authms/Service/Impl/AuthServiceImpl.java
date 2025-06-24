package org.example.authms.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.authms.Dto.LoginRequest;
import org.example.authms.Dto.RegisterRequestDto;
import org.example.authms.Model.User;
import org.example.authms.Repo.UserRepository;
import org.example.authms.Service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final Map<String, OtpEntry> otpStorage = new ConcurrentHashMap<>();
    private final MailServiceImpl mailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private record OtpEntry(String otp, Instant createdAt) {}

    @Override
    public Mono<Void> sendOtp(String email) {
        return Mono.fromSupplier(() -> {
            String otp = String.format("%04d", new Random().nextInt(10000));
            otpStorage.put(email, new OtpEntry(otp, Instant.now()));
            return otp;
        }).flatMap(otp -> mailService.sendOtpMail(email, otp));
    }

    @Override
    public Mono<Object> register(RegisterRequestDto request) {
        return Mono.fromCallable(() -> {
            OtpEntry entry = otpStorage.get(request.getEmail());
            if (entry == null || !entry.otp().equals(request.getOtp())) {
                throw new RuntimeException("Invalid or missing OTP");
            }
            if (Instant.now().isAfter(entry.createdAt().plusSeconds(300))) {
                throw new RuntimeException("OTP expired");
            }

            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already registered");
            }

            User user = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .phone(request.getPhone())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .isVerified(true)
                    .build();

            userRepository.save(user);
            otpStorage.remove(request.getEmail());

            return "User registered successfully.";
        });
    }

    // 3. Login
    @Override
    public Mono<Object> login(LoginRequest request) {
        return Mono.fromSupplier(() -> {
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid password");
            }

            if (!user.isVerified()) {
                throw new RuntimeException("User not verified. Please verify OTP.");
            }
            return "Login successful";
        });
    }
}
