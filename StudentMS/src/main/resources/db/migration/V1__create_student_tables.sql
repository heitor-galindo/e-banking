CREATE TABLE student
(
    id             BIGSERIAL PRIMARY KEY,
    first_name     TEXT    NOT NULL,
    school         TEXT    NOT NULL,
    age            INTEGER NOT NULL,
    reservation_id BIGINT
);
