DROP DATABASE IF EXISTS feature_flag_db;
CREATE DATABASE feature_flag_db;
USE feature_flag_db;

-- 1. USERS
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. USER ROLES (The Fix: No ID, just the Enum string)
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL, -- e.g., 'ADMIN', 'DEVELOPER'
    FOREIGN KEY (user_id) REFERENCES users(id),
    UNIQUE (user_id, role)
);

-- 3. ENVIRONMENTS
CREATE TABLE environments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    is_protected BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. FEATURE FLAGS
CREATE TABLE feature_flags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    flag_key VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    flag_type ENUM('BOOLEAN', 'PERCENTAGE', 'TARGETED') NOT NULL,
    archived BOOLEAN DEFAULT FALSE,
    created_by BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(id)
);

-- 5. FLAG CONFIGURATION PER ENVIRONMENT
CREATE TABLE feature_flag_environments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    feature_flag_id BIGINT NOT NULL,
    environment_id BIGINT NOT NULL,
    enabled BOOLEAN DEFAULT FALSE,
    default_value BOOLEAN DEFAULT FALSE,
    last_modified_by BIGINT,
    last_modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (feature_flag_id, environment_id),
    FOREIGN KEY (feature_flag_id) REFERENCES feature_flags(id),
    FOREIGN KEY (environment_id) REFERENCES environments(id),
    FOREIGN KEY (last_modified_by) REFERENCES users(id)
);

-- 6. RULES
CREATE TABLE flag_rules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    feature_flag_env_id BIGINT NOT NULL,
    rule_type ENUM('USER_TARGET', 'ROLE_TARGET', 'PERCENTAGE') NOT NULL,
    rule_order INT NOT NULL,
    percentage_value INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (feature_flag_env_id)
        REFERENCES feature_flag_environments(id)
);

-- 7. RULE CONDITIONS
CREATE TABLE rule_conditions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rule_id BIGINT NOT NULL,
    condition_key VARCHAR(50) NOT NULL,
    condition_value VARCHAR(255) NOT NULL,
    FOREIGN KEY (rule_id) REFERENCES flag_rules(id)
);

-- 8. CLIENT APPS
CREATE TABLE client_apps (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    api_key VARCHAR(255) NOT NULL UNIQUE,
    environment_id BIGINT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (environment_id) REFERENCES environments(id)
);

-- 9. AUDIT LOGS
CREATE TABLE audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    entity_type VARCHAR(50) NOT NULL,
    entity_id BIGINT NOT NULL,
    action VARCHAR(50) NOT NULL,
    old_value JSON,
    new_value JSON,
    performed_by BIGINT,
    performed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (performed_by) REFERENCES users(id)
);

-- 10. OPTIONAL VERSIONS
CREATE TABLE flag_versions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    feature_flag_env_id BIGINT NOT NULL,
    version_number INT NOT NULL,
    snapshot JSON NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (feature_flag_env_id)
        REFERENCES feature_flag_environments(id)
);

-- INDEXES
CREATE INDEX idx_flag_key ON feature_flags(flag_key);
CREATE INDEX idx_env_name ON environments(name);
CREATE INDEX idx_rule_order ON flag_rules(feature_flag_env_id, rule_order);
CREATE INDEX idx_audit_entity ON audit_logs(entity_type, entity_id);