package ru.grigoriev.cloudstorage.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import ru.grigoriev.cloudstorage.jwt_token.jwt.JwtTokenProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static ru.grigoriev.cloudstorage.DataForTest.*;

@ExtendWith(MockitoExtension.class)
public class AuthTest {

    @InjectMocks
    private AuthService authService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void loginUserTest() {
        given(jwtTokenProvider.createToken(LOGIN)).willReturn(TOKEN);
        assertEquals(TOKEN, authService.login(AUTHENTICATION_GOOD_REQUEST));
    }
}