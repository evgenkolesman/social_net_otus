CREATE TABLE sno_friends
(
    id serial primary key ,
    first_login text not null ,
    second_login text not null ,
    active boolean
);

