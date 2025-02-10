package com.example.apigateway.Mapper;

import com.example.apigateway.Model.AuthResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.stream.Collectors;

import static com.example.apigateway.Mapper.DateMapper.DATE_MAPPER;

public enum AuthMapper {
    AUTH_MAPPER;

    public AuthResponse buildAuthResponse(OidcUser oidcUser ,
                                          OAuth2AuthorizedClient client) {

       return AuthResponse.builder()
               .userId(oidcUser.getEmail())
               .accessToken(client.getAccessToken().getTokenValue())
               .refreshToken(client.getRefreshToken().getTokenValue())
               .expiresAt(DATE_MAPPER.toLocalDateTime(client.getAccessToken().getExpiresAt()))
               .authorities(oidcUser.getAuthorities()
                       .stream()
                       .map(GrantedAuthority::getAuthority)
                       .toList()
               )

                .build();



    }
}


