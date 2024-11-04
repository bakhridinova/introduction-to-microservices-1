create sequence song_metadata_seq;

create table if not exists song_metadata (
    id serial primary key,
    name varchar(255),
    artist varchar(255),
    album varchar(255),
    length varchar(255),
    year varchar(255),
    resource_id integer
);