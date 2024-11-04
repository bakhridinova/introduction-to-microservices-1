create sequence resources_seq;

create table if not exists resources (
    id serial primary key,
    data bytea,
    description text
);