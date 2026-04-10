INSERT INTO promo_codes (code, description, discount_type, discount_value, active, expires_at) VALUES
('WELCOME10', 'Ten percent off first order', 'PERCENT', 10.00, TRUE, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 90 DAY)),
('CARE20', 'Twenty dollars off service package orders', 'FIXED', 20.00, TRUE, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 45 DAY))
ON DUPLICATE KEY UPDATE code = VALUES(code);

INSERT INTO wishlist_items (user_id, item_type, item_slug, item_name, image_url, unit_price) VALUES
(2, 'PRODUCT', 'grain-free-puppy-feast', 'Grain-Free Puppy Feast', '/images/products/food-bowl.svg', 29.99),
(2, 'PET', 'mochi-ragdoll', 'Mochi', '/images/pets/mochi.svg', 600.00)
ON DUPLICATE KEY UPDATE item_slug = VALUES(item_slug);

INSERT INTO orders (owner_reference, user_id, customer_name, customer_email, customer_phone, shipping_address, city, state, postal_code, notes, status, payment_status, payment_reference, subtotal, discount_amount, total_amount, promo_code) VALUES
('user-2', 2, 'Jamie Carter', 'jamie@example.com', '+1 555-0110', '17 Willow Lane', 'Austin', 'Texas', '73301', 'Leave at front desk', 'DELIVERED', 'PAID', 'sandbox-seed-001', 42.49, 4.25, 38.24, 'WELCOME10')
ON DUPLICATE KEY UPDATE payment_reference = VALUES(payment_reference);

INSERT INTO inquiries (user_id, item_slug, item_type, customer_name, customer_email, phone, message, status) VALUES
(2, 'luna-golden-retriever', 'PET', 'Jamie Carter', 'jamie@example.com', '+1 555-0110', 'Is Luna comfortable around children and apartment living?', 'NEW')
ON DUPLICATE KEY UPDATE item_slug = VALUES(item_slug);

INSERT INTO service_bookings (user_id, service_slug, service_name, pet_name, pet_type, appointment_date, notes, customer_name, customer_email, customer_phone, status) VALUES
(2, 'signature-grooming', 'Signature Grooming', 'Buddy', 'Dog', DATE_ADD(CURRENT_DATE, INTERVAL 3 DAY), 'Sensitive skin, please use hypoallergenic shampoo.', 'Jamie Carter', 'jamie@example.com', '+1 555-0110', 'REQUESTED')
ON DUPLICATE KEY UPDATE service_slug = VALUES(service_slug);

INSERT INTO adoption_requests (user_id, pet_slug, pet_name, customer_name, customer_email, customer_phone, home_type, experience_level, notes, status) VALUES
(2, 'mochi-ragdoll', 'Mochi', 'Jamie Carter', 'jamie@example.com', '+1 555-0110', 'Apartment', 'Intermediate', 'I work from home and can provide a quiet indoor setup.', 'NEW')
ON DUPLICATE KEY UPDATE pet_slug = VALUES(pet_slug);

INSERT INTO contact_messages (user_id, type, name, email, subject, message, status) VALUES
(NULL, 'CONTACT', 'Taylor Moss', 'taylor@example.com', 'Store Hours', 'Do you have weekend boarding drop-off availability?', 'NEW'),
(NULL, 'NEWSLETTER', 'Alicia Grant', 'alicia@example.com', 'Newsletter Signup', 'Please subscribe me to new arrivals and care tips.', 'NEW')
ON DUPLICATE KEY UPDATE email = VALUES(email);
