create table if not exists todo (
    id serial primary key,
    description text,
    finished boolean
);