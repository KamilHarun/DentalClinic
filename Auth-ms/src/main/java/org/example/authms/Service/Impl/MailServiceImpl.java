package org.example.authms.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.authms.Service.MailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class MailServiceImpl  implements MailService {
    private final JavaMailSender mailSender;

    public Mono<Void> sendOtpMail(String toEmail, String otp) {
        return Mono.fromRunnable(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Your OTP Code");
            message.setText("Your OTP code is: " + otp + "\nThis code is valid for 5 minutes.");
            mailSender.send(message);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }
}