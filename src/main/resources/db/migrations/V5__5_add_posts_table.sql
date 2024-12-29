CREATE TABLE sno_posts
(
    id uuid primary key default gen_random_uuid(),
    login text not null ,
    text_post text,
    time_modified timestamp,
    active boolean
);

