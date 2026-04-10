CREATE DATABASE IF NOT EXISTS petshop;

CREATE TABLE IF NOT EXISTS carts (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    owner_reference VARCHAR(120) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS cart_items (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    item_type VARCHAR(20) NOT NULL,
    item_slug VARCHAR(120) NOT NULL,
    item_name VARCHAR(180) NOT NULL,
    image_url VARCHAR(255),
    unit_price NUMERIC(10,2) NOT NULL,
    quantity INT NOT NULL
);

ALTER TABLE cart_items
    ADD CONSTRAINT fk_cart_items_cart
    FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE;

CREATE TABLE IF NOT EXISTS promo_codes (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(60) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL,
    discount_type VARCHAR(20) NOT NULL,
    discount_value NUMERIC(10,2) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    expires_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS orders (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    owner_reference VARCHAR(120) NOT NULL,
    user_id BIGINT,
    customer_name VARCHAR(120) NOT NULL,
    customer_email VARCHAR(120) NOT NULL,
    customer_phone VARCHAR(40) NOT NULL,
    shipping_address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    notes VARCHAR(2000),
    status VARCHAR(20) NOT NULL,
    payment_status VARCHAR(20) NOT NULL,
    payment_reference VARCHAR(120),
    subtotal NUMERIC(10,2) NOT NULL,
    discount_amount NUMERIC(10,2) NOT NULL,
    total_amount NUMERIC(10,2) NOT NULL,
    promo_code VARCHAR(60),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    item_type VARCHAR(20) NOT NULL,
    item_slug VARCHAR(120) NOT NULL,
    item_name VARCHAR(180) NOT NULL,
    image_url VARCHAR(255),
    unit_price NUMERIC(10,2) NOT NULL,
    quantity INT NOT NULL,
    line_total NUMERIC(10,2) NOT NULL
);

ALTER TABLE order_items
    ADD CONSTRAINT fk_order_items_order
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE;

CREATE TABLE IF NOT EXISTS payment_transactions (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    provider VARCHAR(60) NOT NULL,
    provider_reference VARCHAR(120) NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL,
    paid_at TIMESTAMP,
    raw_response VARCHAR(2000)
);

ALTER TABLE payment_transactions
    ADD CONSTRAINT fk_payment_transactions_order
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE;

CREATE TABLE IF NOT EXISTS wishlist_items (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    item_type VARCHAR(20) NOT NULL,
    item_slug VARCHAR(120) NOT NULL,
    item_name VARCHAR(180) NOT NULL,
    image_url VARCHAR(255),
    unit_price NUMERIC(10,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, item_slug, item_type)
);

CREATE TABLE IF NOT EXISTS inquiries (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    item_slug VARCHAR(120) NOT NULL,
    item_type VARCHAR(40) NOT NULL,
    customer_name VARCHAR(120) NOT NULL,
    customer_email VARCHAR(120) NOT NULL,
    phone VARCHAR(40),
    message VARCHAR(2000) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS service_bookings (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    service_slug VARCHAR(120) NOT NULL,
    service_name VARCHAR(120) NOT NULL,
    pet_name VARCHAR(120) NOT NULL,
    pet_type VARCHAR(80) NOT NULL,
    appointment_date DATE NOT NULL,
    notes VARCHAR(2000),
    customer_name VARCHAR(120) NOT NULL,
    customer_email VARCHAR(120) NOT NULL,
    customer_phone VARCHAR(40) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS adoption_requests (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    pet_slug VARCHAR(120) NOT NULL,
    pet_name VARCHAR(120) NOT NULL,
    customer_name VARCHAR(120) NOT NULL,
    customer_email VARCHAR(120) NOT NULL,
    customer_phone VARCHAR(40) NOT NULL,
    home_type VARCHAR(80) NOT NULL,
    experience_level VARCHAR(80) NOT NULL,
    notes VARCHAR(2000),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS contact_messages (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    type VARCHAR(40) NOT NULL,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL,
    subject VARCHAR(150) NOT NULL,
    message VARCHAR(3000) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
