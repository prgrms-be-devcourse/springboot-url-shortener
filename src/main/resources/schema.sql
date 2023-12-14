CREATE TABLE IF NOT EXISTS short_urls
(
    id            INT UNSIGNED                        NOT NULL AUTO_INCREMENT,
    value         CHAR(10)                            NOT NULL,
    origin_url    VARCHAR(255)                        NOT NULL,
    algorithm     VARCHAR(50)                         NOT NULL,
    request_count INT UNSIGNED                        NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (value)
);
