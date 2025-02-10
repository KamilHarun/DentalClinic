package com.example.apigateway.Model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AuthResponse {
    String accessToken;
    String userId;
    String refreshToken;
    LocalDateTime expiresAt;
    Collection<String> authorities;

}
