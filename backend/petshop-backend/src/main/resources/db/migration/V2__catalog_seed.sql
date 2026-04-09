INSERT INTO product_categories (name, slug, description) VALUES
('Food', 'food', 'Premium nutrition and daily essentials.'),
('Toys', 'toys', 'Playful enrichment toys for pets of every size.'),
('Grooming', 'grooming', 'Salon-grade grooming and care items.'),
('Accessories', 'accessories', 'Travel, home, and comfort accessories.')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO pet_categories (name, slug, description) VALUES
('Dogs', 'dogs', 'Friendly companions ready for loving homes.'),
('Cats', 'cats', 'Calm, playful, and affectionate feline friends.'),
('Birds', 'birds', 'Colorful birds raised with attentive care.')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO products (category_id, name, slug, description, supplier_name, price, stock_quantity, featured, active, image_url) VALUES
((SELECT id FROM product_categories WHERE slug = 'food'), 'Grain-Free Puppy Feast', 'grain-free-puppy-feast', 'A protein-rich food blend designed for growing puppies with sensitive digestion.', 'Pawsitive Nutrition', 29.99, 42, TRUE, TRUE, '/images/products/food-bowl.svg'),
((SELECT id FROM product_categories WHERE slug = 'toys'), 'Interactive Tug Rope', 'interactive-tug-rope', 'Durable braided rope toy built for energetic play sessions and training rewards.', 'Happy Tails Supply', 12.50, 68, TRUE, TRUE, '/images/products/tug-rope.svg'),
((SELECT id FROM product_categories WHERE slug = 'grooming'), 'Soothing Coat Care Kit', 'soothing-coat-care-kit', 'A gentle shampoo and brush pairing for healthy skin and glossy coats.', 'Clean Coat Co.', 21.75, 37, FALSE, TRUE, '/images/products/grooming-kit.svg'),
((SELECT id FROM product_categories WHERE slug = 'accessories'), 'Travel Comfort Carrier', 'travel-comfort-carrier', 'Ventilated soft carrier with plush base and secure travel straps.', 'Urban Pet Gear', 54.00, 18, TRUE, TRUE, '/images/products/carrier.svg')
ON DUPLICATE KEY UPDATE slug = VALUES(slug);

INSERT INTO pets (category_id, name, slug, breed, age_in_months, gender, price, sale_type, description, vaccinated, available, featured, image_url) VALUES
((SELECT id FROM pet_categories WHERE slug = 'dogs'), 'Luna', 'luna-golden-retriever', 'Golden Retriever', 10, 'Female', 850.00, 'Adoption', 'Luna is a warm, social golden retriever who loves long walks, children, and learning new tricks.', TRUE, TRUE, TRUE, '/images/pets/luna.svg'),
((SELECT id FROM pet_categories WHERE slug = 'dogs'), 'Max', 'max-corgi', 'Pembroke Corgi', 8, 'Male', 920.00, 'Sale', 'Max is energetic, clever, and crate-trained with a playful personality and great temperament.', TRUE, TRUE, FALSE, '/images/pets/max.svg'),
((SELECT id FROM pet_categories WHERE slug = 'cats'), 'Mochi', 'mochi-ragdoll', 'Ragdoll', 14, 'Female', 600.00, 'Adoption', 'Mochi is a gentle indoor cat who enjoys window naps, soft toys, and calm company.', TRUE, TRUE, TRUE, '/images/pets/mochi.svg'),
((SELECT id FROM pet_categories WHERE slug = 'birds'), 'Kiwi', 'kiwi-cockatiel', 'Cockatiel', 12, 'Male', 180.00, 'Sale', 'Kiwi is hand-tamed, vocal, and already comfortable with simple commands and daily interaction.', TRUE, TRUE, FALSE, '/images/pets/kiwi.svg')
ON DUPLICATE KEY UPDATE slug = VALUES(slug);

INSERT INTO pet_vaccinations (pet_id, vaccine_name, notes) VALUES
((SELECT id FROM pets WHERE slug = 'luna-golden-retriever'), 'Rabies', 'Completed'),
((SELECT id FROM pets WHERE slug = 'luna-golden-retriever'), 'DHPP', 'Completed'),
((SELECT id FROM pets WHERE slug = 'max-corgi'), 'Rabies', 'Completed'),
((SELECT id FROM pets WHERE slug = 'mochi-ragdoll'), 'FVRCP', 'Completed')
ON DUPLICATE KEY UPDATE vaccine_name = VALUES(vaccine_name);

INSERT INTO services (name, slug, category, short_description, description, price, duration_minutes, featured, active, image_url) VALUES
('Signature Grooming', 'signature-grooming', 'Grooming', 'Bath, brush, trimming, nail care, and finishing spray.', 'A premium grooming session with coat consultation, gentle cleansing, ear care, paw tidy, and finishing polish.', 49.00, 75, TRUE, TRUE, '/images/products/grooming-kit.svg'),
('Puppy Foundations', 'puppy-foundations', 'Training', 'Confidence, leash basics, and social behavior coaching.', 'Perfect for new owners who want a positive start with recall, crate comfort, and household manners.', 65.00, 60, TRUE, TRUE, '/images/products/tug-rope.svg'),
('Wellness Checkup', 'wellness-checkup', 'Veterinary', 'Routine wellness assessment with guidance and summary.', 'A practical checkup covering weight, coat, hydration, dental signs, and follow-up recommendations.', 39.00, 30, TRUE, TRUE, '/images/products/food-bowl.svg'),
('Overnight Boarding', 'overnight-boarding', 'Boarding', 'Safe, supervised overnight stay with playtime and updates.', 'Clean suites, medication tracking, structured play blocks, feeding logs, and photo updates for owners.', 72.00, 1440, FALSE, TRUE, '/images/products/carrier.svg')
ON DUPLICATE KEY UPDATE slug = VALUES(slug);

INSERT INTO blog_posts (title, slug, excerpt, content, category, tags, author_name, featured_image_url, published, published_at) VALUES
('Healthy Pet Nutrition Basics', 'healthy-pet-nutrition-basics', 'A clear guide to choosing balanced meals, reading labels, and building better feeding habits.', 'Balanced nutrition starts with age-appropriate formulas, realistic portion control, and consistency. Focus on ingredient transparency, digestibility, hydration, and gradual transitions when changing food. For puppies and kittens, growth support matters. For adults, weight management and coat health often become leading indicators. Treats should support training, not replace meals.', 'Nutrition', 'nutrition, feeding, wellness', 'Dr. Sana Mehra', '/images/products/food-bowl.svg', TRUE, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 10 DAY)),
('Training Tips for Calm Walks', 'training-tips-for-calm-walks', 'Simple leash training patterns that make daily walks more enjoyable.', 'Calm walks come from repetition, reward timing, and manageable environments. Start indoors with short leash drills, then progress outside in low-distraction spaces. Mark eye contact, reinforce loose-leash moments, and keep sessions short enough to end successfully. Training is a routine, not a one-time event.', 'Training', 'training, dogs, behavior', 'Mira Collins', '/images/pets/luna.svg', TRUE, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 5 DAY))
ON DUPLICATE KEY UPDATE slug = VALUES(slug);

INSERT INTO faq_items (question, answer, category, display_order, active) VALUES
('Do you offer same-day grooming?', 'Yes. Same-day grooming is offered based on appointment capacity. Online booking shows the earliest available slots.', 'Services', 1, TRUE),
('Can I reserve a pet before visiting?', 'You can place an inquiry or adoption request online. A coordinator will contact you to schedule the next step.', 'Pets', 2, TRUE),
('What payment methods do you support?', 'The checkout flow includes a sandbox provider abstraction and can be connected to a live provider later without changing the order flow.', 'Orders', 3, TRUE),
('Do you ship pet products nationwide?', 'Yes. Orders can be prepared for local delivery or nationwide shipping depending on your configuration.', 'Shop', 4, TRUE)
ON DUPLICATE KEY UPDATE question = VALUES(question);

INSERT INTO reviews (reviewer_name, rating, title, body, subject_type, subject_slug, approved) VALUES
('Priya S.', 5, 'A polished, caring experience', 'The grooming team handled our anxious pup with patience and sent thoughtful updates.', 'SERVICE', 'signature-grooming', TRUE),
('Daniel K.', 5, 'Exactly what we needed', 'The nutrition advice and product recommendations were practical, not pushy.', 'PRODUCT', 'grain-free-puppy-feast', TRUE),
('Amrita R.', 5, 'Wonderful adoption support', 'The team answered every question and matched us with the sweetest cat.', 'PET', 'mochi-ragdoll', TRUE)
ON DUPLICATE KEY UPDATE title = VALUES(title);

INSERT INTO promotions (title, summary, badge, discount_text, active, starts_at, ends_at) VALUES
('Spring Wellness Event', 'Save on grooming bundles and wellness essentials this month.', 'Limited Time', '15% Off Care', TRUE, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 2 DAY), DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 20 DAY)),
('New Pet Starter Pack', 'Bundle food, carrier, and training toy for a smoother first week home.', 'Popular', 'Bundle Savings', TRUE, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 1 DAY), DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 30 DAY))
ON DUPLICATE KEY UPDATE title = VALUES(title);

INSERT INTO admin_team_members (name, role_title, bio, photo_url, email, phone, display_order, active) VALUES
('Ariana Flores', 'Founder & Head Care Lead', 'Ariana built the shop around low-stress care, ethical sourcing, and warm customer guidance.', '/images/team/ariana.svg', 'ariana@petshop.local', '+1 555-0101', 1, TRUE),
('Marcus Green', 'Training Specialist', 'Marcus leads behavior programs focused on confidence-building and family routines.', '/images/team/marcus.svg', 'marcus@petshop.local', '+1 555-0102', 2, TRUE),
('Leena Patel', 'Client Experience Manager', 'Leena oversees adoptions, bookings, and service recovery with a calm, organized approach.', '/images/team/leena.svg', 'leena@petshop.local', '+1 555-0103', 3, TRUE)
ON DUPLICATE KEY UPDATE name = VALUES(name);
