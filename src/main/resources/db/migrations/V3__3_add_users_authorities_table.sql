CREATE TABLE users
(
    username text not null unique references sno_user_register (user_id),
    password text not null ,
    enabled boolean
);

CREATE TABLE authorities
(
    username text not null unique references users (username) ,
    authority text
);