CREATE TABLE IF NOT EXISTS short_url (
    transformed_url char(6) PRIMARY KEY,
    original_url varchar(2047) not null,
    visit_count bigint default 0 not null,
    last_visit_time timestamp not null default current_timestamp(6)
);
