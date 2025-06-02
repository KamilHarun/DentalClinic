package org.example.authms.Service.Impl;

import lombok.RequiredArgsConstructor;
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

    // 1. OTP gönderme
    @Override
    public Mono<Void> sendOtp(String email) {
        return Mono.fromSupplier(() -> {
            String otp = String.format("%04d", new Random().nextInt(10000));
            otpStorage.put(email, new OtpEntry(otp, Instant.now()));
            return otp;
        }).flatMap(otp -> mailService.sendOtpMail(email, otp));
    }

    // 2. Register - OTP ile birlikte
    @Override
    public Mono<Object> register(String email, String password, String otp) {
        return Mono.fromCallable(() -> {
            OtpEntry entry = otpStorage.get(email);
            if (entry == null || !entry.otp().equals(otp)) {
                throw new RuntimeException("Invalid or missing OTP");
            }
            if (Instant.now().isAfter(entry.createdAt().plusSeconds(300))) {
                throw new RuntimeException("OTP expired");
            }

            // Email zaten var mı?
            if (userRepository.existsByEmail(email)) {
                throw new RuntimeException("Email already registered");
            }

            // Kullanıcıyı oluştur
            User user = User.builder()
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .isVerified(true)
                    .build();

            userRepository.save(user);
            otpStorage.remove(email); // OTP temizle

            return "User registered successfully.";
        });
    }

    // 3. Login
    @Override
    public Mono<Object> login(String email, String password) {
        return Mono.fromSupplier(() -> {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new RuntimeException("Invalid password");
            }

            if (!user.isVerified()) {
                throw new RuntimeException("User not verified. Please verify OTP.");
            }

            return "Login successful";
        });
    }
}
