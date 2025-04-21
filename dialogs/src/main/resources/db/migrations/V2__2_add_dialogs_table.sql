CREATE TABLE sno_dialogs
(
    id     uuid primary key default gen_random_uuid(),
    "from" text,
    "to"   text,
    "text" text,
    time_modified timestamp default now()
)
