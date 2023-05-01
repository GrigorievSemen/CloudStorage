package ru.grigoriev.cloudstorage;

import ru.grigoriev.cloudstorage.model.File;
import ru.grigoriev.cloudstorage.web.request.AuthenticationRequest;

import java.util.List;
import java.util.UUID;

public class DataForTest {
    public static final String LOGIN = "user1@yandex.com";
    public static final String WRONG_LOGIN = "wrong@yandex.com";
    public static final String PASSWORD = "test1";
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final AuthenticationRequest AUTHENTICATION_GOOD_REQUEST = new AuthenticationRequest(LOGIN, PASSWORD);
    public static final AuthenticationRequest AUTHENTICATION_WRONG_REQUEST = new AuthenticationRequest(WRONG_LOGIN, PASSWORD);
    public static final String TOKEN = UUID.randomUUID().toString();
    public static final String FILE_NAME = "name";
    public static final byte[] DATA = UUID.randomUUID().toString().getBytes();
    public static final List<File> FILE_LIST = List.of(getFile());
    public static final int LIMIT = 1;

    public static File getFile() {
        return File.builder()
                .fileName(FILE_NAME)
                .data(DATA)
                .size(36L)
                .build();
    }
}
