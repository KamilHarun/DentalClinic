package com.example.apigateway.controller;

import com.example.apigateway.Mapper.AuthMapper;
import com.example.apigateway.Model.AuthResponse;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

//    private final WebClient webClient;


    @GetMapping("/login")
    public AuthResponse login(
            Model model,
            @AuthenticationPrincipal OidcUser oidcUser,
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient oAuth2AuthorizedClient
    ) {
        return AuthMapper.AUTH_MAPPER.buildAuthResponse(oidcUser, oAuth2AuthorizedClient);

    }
}

//    @PostMapping("/refresh")
//    public Mono<AuthResponse> refreshToken(@RequestHeader("Authorization") String refreshHeader) {
//        String refreshToken = refreshHeader.replace("Bearer ", "");
//
//        return webClient
//                .post()
//                .uri("https://dev-14999453.okta.com/oauth2/default/v1/token")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                .bodyValue("grant_type=refresh_token&refresh_token=" + refreshToken +
//                        "&client_id=0oan8ht9iqNnXmWyx5d7&client_secret=pq7Q8n3dgzL2SAApzRDpqKhGJzVow30-KagJvtiFVGOCbcmCF4YaBW9S-_52C8sL")
//                .retrieve()
//                .bodyToMono(JsonNode.class)
//                .map(tokenResponse -> {
//                    return AuthResponse.builder()
//                            .accessToken(tokenResponse.get("access_token").asText())
//                            .refreshToken(tokenResponse.get("refresh_token").asText())
//                            .expiresAt(LocalDateTime.now().plusSeconds(tokenResponse.get("expires_in").asLong()))
//                            .userId("unknown") // refresh zamanı user info olmur, əgər userId almaq istəsən, ayrıca sorğu etməlisən
//                            .authorities(List.of("ROLE_USER"))
//                            .build();
//                });
//    }
//}




