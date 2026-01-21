CREATE TABLE "user"
(
    id             BIGSERIAL PRIMARY KEY,
    first_name     TEXT    NOT NULL,
    sur_name       TEXT    NOT NULL,
    email          TEXT    NOT NULL,
    age            INTEGER NOT NULL,
    account_id BIGINT
);