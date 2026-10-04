-- V11: Restructure payment tables to match updated ERD
-- Changes:
--   payments: invoice_id + receipt_id → booking_id; VARCHAR enums → ENUM types
--   invoices: booking_id → payment_id; add pdf_url column
--   receipts: standalone → add invoice_id FK; rename tour_tittle → tour_title

-- ================================================================
-- 1. DROP old foreign keys and columns from payments
-- ================================================================
ALTER TABLE payments
    DROP FOREIGN KEY fk_payment_invoice,
    DROP FOREIGN KEY fk_payment_receipt,
    DROP COLUMN invoice_id,
    DROP COLUMN receipt_id;

ALTER TABLE payments
    ADD COLUMN booking_id BIGINT NOT NULL AFTER id,
    ADD CONSTRAINT fk_payment_booking FOREIGN KEY (booking_id) REFERENCES bookings(id);

ALTER TABLE payments
    MODIFY COLUMN payment_method ENUM('card','bank_transfer','aba_pay') NULL,
    MODIFY COLUMN payment_status ENUM('pending','paid','failed','refunded') NULL DEFAULT 'pending';

-- ================================================================
-- 2. Restructure invoices: drop booking_id, add payment_id + pdf_url
-- ================================================================
ALTER TABLE invoices
    DROP FOREIGN KEY fk_invoice_booking,
    DROP COLUMN booking_id;

ALTER TABLE invoices
    ADD COLUMN payment_id BIGINT NOT NULL AFTER id,
    ADD COLUMN pdf_url VARCHAR(255) NULL,
    ADD CONSTRAINT fk_invoice_payment FOREIGN KEY (payment_id) REFERENCES payments(id);

-- ================================================================
-- 3. Add invoice_id FK to receipts; fix typo tour_tittle → tour_title
-- ================================================================
ALTER TABLE receipts
    ADD COLUMN invoice_id BIGINT NOT NULL AFTER id,
    ADD CONSTRAINT fk_receipt_invoice FOREIGN KEY (invoice_id) REFERENCES invoices(id);

ALTER TABLE receipts
    CHANGE COLUMN tour_tittle tour_title VARCHAR(255);