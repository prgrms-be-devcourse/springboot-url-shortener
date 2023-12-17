CREATE TABLE IF NOT EXISTS short_url (
    transformed_url CHAR(6) PRIMARY KEY,
    original_url VARCHAR(2047) NOT NULL,
    visit_count BIGINT DEFAULT 0 NOT NULL,
    last_visit_time TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    INDEX idx_original_url (original_url(255))
);
