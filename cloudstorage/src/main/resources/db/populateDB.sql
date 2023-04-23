DELETE
FROM users;

INSERT INTO users (email, password)
VALUES ( 'user1@yandex.com',
        '$2a$10$.NUmdJBMsx4OzbxZPnPpfeYSCw9kiek3lhvjp9nCjxugVG0ioVwf.'), -- 1 password test1
       ( 'user2@yandex.com',
        '$2a$10$/yRivUr0N8X23Uz1ASSNxOrCzupdmBdO0o.xBp4GQKoLeli1vDcXi'); -- 2 password test2

