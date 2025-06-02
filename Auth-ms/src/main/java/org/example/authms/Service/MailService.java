package org.example.authms.Service;

import reactor.core.publisher.Mono;

public interface MailService {

     Mono<Void> sendOtpMail(String toEmail, String otp);
}
