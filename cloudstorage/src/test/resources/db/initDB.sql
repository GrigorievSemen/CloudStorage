DROP TABLE IF EXISTS public.files;
DROP TABLE IF EXISTS public.users;

CREATE TABLE public.users
(
    id       INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email    VARCHAR(255) UNIQUE           NOT NULL,
    password VARCHAR(255)                  NOT NULL,
    enabled  BOOLEAN      DEFAULT TRUE     NOT NULL,
    status   VARCHAR(255) DEFAULT 'ACTIVE' NOT NULL
);
CREATE INDEX ON public.users (email);

CREATE TABLE public.files
(
    id        INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    size      BIGINT       NOT NULL,
    type      VARCHAR(255) NOT NULL,
    holder    VARCHAR      NOT NULL,
    data      bytea        NOT NULL,
    FOREIGN KEY (holder) REFERENCES public.users (email) ON DELETE CASCADE
);
CREATE INDEX ON public.files (holder);