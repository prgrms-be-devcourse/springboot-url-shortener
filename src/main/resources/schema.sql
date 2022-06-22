DROP TABLE IF EXISTS urls;

CREATE TABLE urls
(
    id           BIGINT      NOT NULL auto_increment,
    url MEDIUMTEXT  NOT NULL,
    short_url    VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);