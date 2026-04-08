CREATE SCHEMA IF NOT EXISTS commerce;

CREATE TABLE IF NOT EXISTS carts (
    id BIGSERIAL PRIMARY KEY,
    owner_reference VARCHAR(120) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS cart_items (
    id BIGSERIAL PRIMARY KEY,
    cart_id BIGINT NOT NULL REFERENCES carts(id) ON DELETE CASCADE,
    item_type VARCHAR(20) NOT NULL,
    item_slug VARCHAR(120) NOT NULL,
    item_name VARCHAR(180) NOT NULL,
    image_url VARCHAR(255),
    unit_price NUMERIC(10,2) NOT NULL,
    quantity INT NOT NULL
);

CREATE TABLE IF NOT EXISTS promo_codes (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(60) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL,
    discount_type VARCHAR(20) NOT NULL,
    discount_value NUMERIC(10,2) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    expires_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS orders (
    id BIGSERIAL PRIMARY KEY,
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
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    item_type VARCHAR(20) NOT NULL,
    item_slug VARCHAR(120) NOT NULL,
    item_name VARCHAR(180) NOT NULL,
    image_url VARCHAR(255),
    unit_price NUMERIC(10,2) NOT NULL,
    quantity INT NOT NULL,
    line_total NUMERIC(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS payment_transactions (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    provider VARCHAR(60) NOT NULL,
    provider_reference VARCHAR(120) NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL,
    paid_at TIMESTAMP,
    raw_response VARCHAR(2000)
);

CREATE TABLE IF NOT EXISTS wishlist_items (
    id BIGSERIAL PRIMARY KEY,
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
    id BIGSERIAL PRIMARY KEY,
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
    id BIGSERIAL PRIMARY KEY,
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
    id BIGSERIAL PRIMARY KEY,
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
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    type VARCHAR(40) NOT NULL,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL,
    subject VARCHAR(150) NOT NULL,
    message VARCHAR(3000) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

