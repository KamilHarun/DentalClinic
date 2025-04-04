package com.example.apigateway.controller;

import com.example.apigateway.Mapper.AuthMapper;
import com.example.apigateway.Model.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {


    @GetMapping("/login")
    public AuthResponse login(
            Model model,
            @AuthenticationPrincipal OidcUser oidcUser,
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient oAuth2AuthorizedClient
    ) {
        return AuthMapper.AUTH_MAPPER.buildAuthResponse(oidcUser, oAuth2AuthorizedClient);

    }
}


