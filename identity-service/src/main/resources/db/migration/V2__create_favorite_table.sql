


CREATE TABLE favorite
(
    user_id     UUID        NOT NULL ,
    product_id  UUID        NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE   NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_favorite PRIMARY KEY (user_id, product_id),
    CONSTRAINT fk_favorite_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE

);