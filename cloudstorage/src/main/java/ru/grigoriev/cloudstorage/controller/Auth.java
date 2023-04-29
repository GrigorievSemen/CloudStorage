package ru.grigoriev.cloudstorage.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.grigoriev.cloudstorage.service.AuthService;
import ru.grigoriev.cloudstorage.web.request.AuthenticationRequest;
import ru.grigoriev.cloudstorage.web.response.AuthResponse;

@Slf4j
@RestController
@RequiredArgsConstructor

public class Auth {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthenticationRequest request) {
        log.info("IN login");
        String token = authService.login(request);
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<?> logoutUser(@RequestHeader("auth-token") String token) {
        log.info("IN logout");
        authService.logoutUser(token);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
