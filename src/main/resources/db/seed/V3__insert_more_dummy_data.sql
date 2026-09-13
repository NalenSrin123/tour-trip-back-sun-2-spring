INSERT INTO bookings (user_id, tour_schedule_id, total_price, special_requests, booking_status, booking_type, member_count)
VALUES
(3, 110, 300.00, 'Vegetarian meals', 'Confirmed', 'Individual', 1),
(4, 102, 900.00, 'Honeymoon suite', 'Confirmed', 'Family', 2),
(5, 103, 150.00, 'No suggestions', 'Pending', 'Individual', 1),
(2, 104, 600.00, 'Wheelchair access', 'Cancelled', 'Team', 4),
(6, 105, 250.00, 'Early check-in', 'Confirmed', 'Individual', 1);

INSERT INTO participants (booking_id, age_group, name, sex)
VALUES
(3, 'Adult', 'Ly Sreymom', 'Female'),
(4, 'Adult', 'Heng Vibol', 'Male'),
(4, 'Adult', 'Kim Srey Pov', 'Female'),
(5, 'Adult', 'Chea Rithy', 'Male'),
(6, 'Adult', 'Nov Sokha', 'Male'),
(6, 'Adult', 'Pov Malis', 'Female'),
(6, 'Child', 'Nov Ratha', 'Male'),
(6, 'Adult', 'San Dara', 'Female'),
(7, 'Adult', 'Meas Bunthoeun', 'Male');

INSERT INTO invoices (booking_id, invoice_no, sub_total, tax_amount, total_amount)
VALUES
(3, 'INV-2026-0003', 280.00, 20.00, 300.00),
(4, 'INV-2026-0004', 850.00, 50.00, 900.00),
(6, 'INV-2026-0006', 560.00, 40.00, 600.00),
(7, 'INV-2026-0007', 230.00, 20.00, 250.00);

INSERT INTO receipts (receipt_no, tour_tittle, tour_date, num_travelers, sub_total, tax_amount, total_paid, payment_method, pdf_url)
VALUES
('REC-2026-0002', 'Koh Rong Beach Tour', '2026-10-01 07:30:00', 1, 280.00, 20.00, 300.00, 'card', '/uploads/receipts/REC-2026-0002.pdf'),
('REC-2026-0003', 'Siem Reap Family Package', '2026-10-05 09:00:00', 2, 850.00, 50.00, 900.00, 'bank_transfer', '/uploads/receipts/REC-2026-0003.pdf');

INSERT INTO payments (invoice_id, receipt_id, amount, payment_method, payment_status, transaction_id, payment_date)
VALUES
(3, 2, 300.00, 'card', 'paid', 'CARD-A1B2C3D4', '2026-09-20 14:00:00'),
(4, 3, 900.00, 'bank_transfer', 'paid', 'BANK-B2C3D4E5', '2026-09-21 10:15:00'),
(5, NULL, 600.00, 'aba_pay', 'failed', NULL, NULL),
(6, NULL, 250.00, 'card', 'pending', NULL, NULL);
