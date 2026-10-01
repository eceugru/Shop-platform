

CREATE TABLE users
(
    id          UUID            NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    email       VARCHAR(100)    NOT NULL,
    password    VARCHAR(200)    NOT NULL,
    role        VARCHAR(100)      NOT NULL DEFAULT 'CUSTOMER',

    CONSTRAINT users_email UNIQUE (email),

    CONSTRAINT pk_user PRIMARY KEY (id)
);

