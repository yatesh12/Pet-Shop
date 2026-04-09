CREATE DATABASE IF NOT EXISTS identity;

CREATE TABLE IF NOT EXISTS identity.roles (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(40) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS identity.users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    phone VARCHAR(40),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS identity.user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

ALTER TABLE identity.user_roles
    ADD CONSTRAINT fk_identity_user_roles_user
    FOREIGN KEY (user_id) REFERENCES identity.users(id) ON DELETE CASCADE;

ALTER TABLE identity.user_roles
    ADD CONSTRAINT fk_identity_user_roles_role
    FOREIGN KEY (role_id) REFERENCES identity.roles(id) ON DELETE CASCADE;

CREATE TABLE IF NOT EXISTS identity.addresses (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    label VARCHAR(80) NOT NULL,
    line_one VARCHAR(180) NOT NULL,
    line_two VARCHAR(180),
    city VARCHAR(80) NOT NULL,
    state VARCHAR(80) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(80) NOT NULL,
    default_address BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE identity.addresses
    ADD CONSTRAINT fk_identity_addresses_user
    FOREIGN KEY (user_id) REFERENCES identity.users(id) ON DELETE CASCADE;

CREATE TABLE IF NOT EXISTS identity.auth_sessions (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(120) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE identity.auth_sessions
    ADD CONSTRAINT fk_identity_auth_sessions_user
    FOREIGN KEY (user_id) REFERENCES identity.users(id) ON DELETE CASCADE;
