CREATE TABLE sno_user_register
(
    id     uuid primary key default gen_random_uuid(),
    username text,
    first_name   text,
    second_name text,
    birthdate date,
    biography text,
    city text,
    password text
)