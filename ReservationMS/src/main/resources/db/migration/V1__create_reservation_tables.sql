CREATE TABLE reservation
(
    id_reservation BIGSERIAL PRIMARY KEY,
    student_id     BIGINT    NOT NULL,
    creation_date  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
