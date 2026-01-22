CREATE TABLE user_profile
(
    keycloak_id TEXT NOT NULL PRIMARY KEY,
    user_name   TEXT NOT NULL,
    full_name   TEXT NOT NULL,
    email       TEXT NOT NULL,
    phone       TEXT NOT NULL
);
