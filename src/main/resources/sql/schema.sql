create table urls
(
    id            bigint generated by default as identity,
    created_at    timestamp    not null,
    modified_at   timestamp,
    hash          varchar(2048),
    hash_type     varchar(255) not null,
    org_url       varchar(2048),
    request_count bigint       not null check (request_count >= 0),
    primary key (id)
);