create sequence URL_SEQ start with 927472868 increment by 1;

create TABLE URL(
    id varchar(7) not null,
    original_url varchar(512) not null,
    shortUrl varchar(20) not null,
    view_count bigint,
    created_date timestamp,
    primary key (id)
);
