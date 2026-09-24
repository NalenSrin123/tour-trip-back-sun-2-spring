ALTER TABLE users
    ADD COLUMN telegram_chat_id VARCHAR(100) NULL AFTER remember_token;