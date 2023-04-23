package ru.grigoriev.cloudstorage.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.grigoriev.cloudstorage.jwt_token.jwt.JwtTokenProvider;
import ru.grigoriev.cloudstorage.web.request.AuthenticationRequest;
import ru.grigoriev.cloudstorage.web.response.AuthResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@Transactional(readOnly = true)
public class AuthRestController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthenticationRequest requestDto) {
        log.info("IN login");
        String email = requestDto.getLogin();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid name or password");
        }

        String token = jwtTokenProvider.createToken(email);

        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    @GetMapping("login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
