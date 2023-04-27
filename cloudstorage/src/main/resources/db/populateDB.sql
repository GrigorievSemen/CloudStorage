DELETE
FROM users;

CREATE EXTENSION if not exists pgcrypto;

INSERT INTO users (email, password)
VALUES ('user1@yandex.com', crypt('test1', gen_salt('bf'))),
       ('user2@yandex.com', crypt('test2', gen_salt('bf')));

