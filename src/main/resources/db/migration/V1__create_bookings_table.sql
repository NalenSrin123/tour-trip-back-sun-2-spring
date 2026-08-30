
CREATE TABLE bookings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    tour_schedule_id BIGINT,
    total_price DECIMAL(10,2),
    special_requests TEXT,
    booking_status VARCHAR(50),
    booking_type VARCHAR(50),
    member_count INT,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE participants (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    age_group VARCHAR(50),
    name VARCHAR(100),
    sex VARCHAR(20),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_participant_booking FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

CREATE TABLE invoices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    invoice_no VARCHAR(100) UNIQUE,
    sub_total DECIMAL(10,2),
    tax_amount DECIMAL(10,2),
    total_amount DECIMAL(10,2),
    issued_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_invoice_booking FOREIGN KEY (booking_id) REFERENCES bookings(id)
);


CREATE TABLE receipts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    receipt_no VARCHAR(100) UNIQUE,
    issued_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    tour_title VARCHAR(255),
    tour_date DATETIME,
    num_travelers INT,
    sub_total DECIMAL(10,2),
    tax_amount DECIMAL(10,2),
    total_paid DECIMAL(10,2),
    payment_method VARCHAR(50),
    pdf_url VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);


CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_id BIGINT NOT NULL,
    receipt_id BIGINT,
    amount DECIMAL(10,2),
    payment_method VARCHAR(50),
    payment_status VARCHAR(50),
    transaction_id VARCHAR(100),
    payment_date DATETIME,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_payment_invoice FOREIGN KEY (invoice_id) REFERENCES invoices(id),
    CONSTRAINT fk_payment_receipt FOREIGN KEY (receipt_id) REFERENCES receipts(id)
);