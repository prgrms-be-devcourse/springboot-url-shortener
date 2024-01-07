CREATE TABLE url
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    short_key    VARCHAR(255) UNIQUE NOT NULL,
    original_url VARCHAR(255)        NOT NULL,
    created_at   TIMESTAMP,
    visit_count  BIGINT
);
