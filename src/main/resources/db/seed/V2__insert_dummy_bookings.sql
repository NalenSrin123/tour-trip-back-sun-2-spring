-- ១. បញ្ចូលទិន្នន័យសាកល្បងទៅក្នុងតារាងកក់ (Bookings)
INSERT INTO bookings (user_id, tour_schedule_id, total_price, special_requests, booking_status, booking_type, member_count) 
VALUES 
(1, 101, 450.00, 'ត្រូវការម្ហូបបួសសម្រាប់មនុស្សចាស់', 'CONFIRMED', 'Family', 3),
(2, 105, 120.00, 'គ្មានសំណូមពរ', 'PENDING', 'Individual', 1);

-- ២. បញ្ចូលទិន្នន័យអ្នកចូលរួម (Participants) ដោយយោងទៅលើ Booking ID លេខ 1 និង 2
INSERT INTO participants (booking_id, age_group, name, sex) 
VALUES 
(1, 'Adult', 'Sok Dara', 'Male'),
(1, 'Adult', 'Chan Thida', 'Female'),
(1, 'Child', 'Sok Visal', 'Male'),
(2, 'Adult', 'Somnang Pich', 'Male');

-- ៣. បញ្ចូលទិន្នន័យវិក្កយបត្រ (Invoices) សម្រាប់ការកក់ទាំងពីរ
INSERT INTO invoices (booking_id, invoice_no, sub_total, tax_amount, total_amount) 
VALUES 
(1, 'INV-2026-0001', 400.00, 50.00, 450.00),
(2, 'INV-2026-0002', 110.00, 10.00, 120.00);

-- ៤. បញ្ចូលទិន្នន័យបង្កាន់ដៃ (Receipts) សម្រាប់តែការកក់ទី១ ដែលបានបង់ប្រាក់រួច (CONFIRMED)
INSERT INTO receipts (receipt_no, tour_tittle, tour_date, num_travelers, sub_total, tax_amount, total_paid, payment_method, pdf_url) 
VALUES 
('REC-2026-0001', 'ដំណើរទេសចរណ៍អង្គរវត្ត ៣ថ្ងៃ ២យប់', '2026-09-15 08:00:00', 3, 400.00, 50.00, 450.00, 'aba_pay', '/uploads/receipts/REC-2026-0001.pdf');

-- ៥. បញ្ចូលទិន្នន័យការទូទាត់ (Payments) 
-- - Payment ទី១ ជោគជ័យ (មានភ្ជាប់ជាមួយទាំង Invoice លេខ 1 និង Receipt លេខ 1)
-- - Payment ទី២ នៅរង់ចាំ (មានតែ Invoice លេខ 2 តែមិនទាន់មាន Receipt ទេ)
INSERT INTO payments (invoice_id, receipt_id, amount, payment_method, payment_status, transaction_id, payment_date) 
VALUES 
(1, 1, 450.00, 'aba_pay', 'paid', 'ABA987654321', '2026-08-23 10:30:00'),
(2, NULL, 120.00, 'card', 'pending', NULL, NULL);