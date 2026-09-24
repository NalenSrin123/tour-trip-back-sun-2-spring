CREATE TABLE personal_access_tokens (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    tokenable_type VARCHAR(255) NOT NULL,
    tokenable_id   VARCHAR(255) NOT NULL,
    name           TEXT         NOT NULL,
    token          VARCHAR(255) NOT NULL,
    abilities      TEXT         NULL,
    last_used_at   TIMESTAMP    NULL,
    expired_at     TIMESTAMP    NULL,
    created_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uq_personal_access_tokens_token UNIQUE (token)
);

CREATE INDEX idx_pat_tokenable ON personal_access_tokens(tokenable_type, tokenable_id);