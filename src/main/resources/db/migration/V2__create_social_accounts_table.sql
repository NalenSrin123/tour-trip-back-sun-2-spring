CREATE TABLE social_accounts (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    user_id      INT NOT NULL,
    provider     VARCHAR(255) NULL,
    provider_id  VARCHAR(255) NULL,
    created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at   TIMESTAMP NULL,
    CONSTRAINT fk_social_accounts_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT uq_social_accounts_provider UNIQUE (provider, provider_id)
);

CREATE INDEX idx_social_accounts_user_id ON social_accounts(user_id);