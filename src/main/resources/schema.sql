CREATE SEQUENCE hibernate_sequence start with 100001 increment by 1;

CREATE TABLE urlinfo
(
    id bigint auto_increment primary key,
    origin_url varchar(1000) not null unique
);

