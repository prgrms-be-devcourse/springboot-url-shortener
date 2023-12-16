CREATE TABLE IF NOT EXISTS short_urls
(
    id            INT UNSIGNED                           NOT NULL AUTO_INCREMENT,
    origin_url    VARCHAR(255)                           NOT NULL,
    url_key       CHAR(10)                               NOT NULL,
    algorithm     VARCHAR(50)                            NOT NULL,
    request_count INT UNSIGNED DEFAULT 0                 NOT NULL,
    created_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (url_key)
);
