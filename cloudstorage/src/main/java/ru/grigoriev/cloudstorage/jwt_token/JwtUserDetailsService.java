package ru.grigoriev.cloudstorage.jwt_token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.grigoriev.cloudstorage.exception.NotFoundException;
import ru.grigoriev.cloudstorage.model.User;
import ru.grigoriev.cloudstorage.repository.UserRepository;
import ru.grigoriev.cloudstorage.jwt_token.jwt.JwtUser;
import ru.grigoriev.cloudstorage.jwt_token.jwt.JwtUserFactory;


import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = Optional.ofNullable(repository.findByEmail(email)
                .orElseThrow(() ->
                        new NotFoundException("User does not exist in the database")));

        JwtUser jwtUser = JwtUserFactory.create(user.get());
        log.info("IN loadUserByUsername - user with username: {} successfully loaded", email);
        return jwtUser;
    }
}
