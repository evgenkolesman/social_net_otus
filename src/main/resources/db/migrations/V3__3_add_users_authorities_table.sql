CREATE TABLE users
(
    username text,
    password text,
    enabled boolean
);

CREATE TABLE authorities
(
    username text,
    authority text
);