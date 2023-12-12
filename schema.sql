create TABLE URL(
    id BIGINT AUTO_INCREMENT,
    long_url VARCHAR(255) NOT NULL UNIQUE,
    short_url CHAR(8) UNIQUE,
    CONSTRAINT pk_url_id PRIMARY KEY (id)
);