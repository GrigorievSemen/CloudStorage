package ru.grigoriev.cloudstorage.jwt_token.jwt;

import ru.grigoriev.cloudstorage.model.Status;
import ru.grigoriev.cloudstorage.model.User;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getPassword(),
                user.getEmail(),
                user.getStatus().equals(Status.ACTIVE)
        );
    }
}
