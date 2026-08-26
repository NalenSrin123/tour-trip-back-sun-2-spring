CREATE TABLE users (
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    name               VARCHAR(255) NOT NULL,
    email              VARCHAR(255) NOT NULL,
    email_verified_at  TIMESTAMP NULL,
    phone              VARCHAR(255) NULL,
    password_hash      VARCHAR(255) NOT NULL,
    status             ENUM('active', 'inactive') NOT NULL DEFAULT 'inactive',
    remember_token     VARCHAR(100) NULL,
    created_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at         TIMESTAMP NULL,
    CONSTRAINT uq_users_email UNIQUE (email)
);