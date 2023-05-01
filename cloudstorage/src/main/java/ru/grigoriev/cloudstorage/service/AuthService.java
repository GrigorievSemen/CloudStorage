package ru.grigoriev.cloudstorage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ru.grigoriev.cloudstorage.jwt_token.jwt.JwtTokenProvider;
import ru.grigoriev.cloudstorage.web.request.AuthenticationRequest;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public String login(AuthenticationRequest request) {
        String email = request.getLogin();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.getPassword()));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid name or password");
        }

        String token = jwtTokenProvider.createToken(email);
        jwtTokenProvider.writeToken(token);
        return token;
    }

    public void logoutUser(String token) {
        jwtTokenProvider.deleteToken(token);
    }
}