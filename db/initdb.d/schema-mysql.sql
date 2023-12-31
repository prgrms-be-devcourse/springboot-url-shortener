
create table url (
    id bigint auto_increment primary key,
    original_url text,
    short_url varchar(255),
    watch_number int
);

CREATE INDEX short_url_index ON url(short_url);
