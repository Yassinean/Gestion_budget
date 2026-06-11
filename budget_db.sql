DROP DATABASE IF EXISTS budget_db;
CREATE DATABASE budget_db;
USE budget_db;

-- =====================================================
-- ACCOUNTS
-- Représente les utilisateurs authentifiés
-- =====================================================

CREATE TABLE accounts (
    account_id BIGINT AUTO_INCREMENT PRIMARY KEY,

    username VARCHAR(100) NOT NULL UNIQUE,

    email VARCHAR(150) NOT NULL UNIQUE,

    password VARCHAR(255) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);

-- =====================================================
-- BUDGETS
-- Un compte peut posséder plusieurs budgets
-- =====================================================

CREATE TABLE budgets (

    budget_id BIGINT AUTO_INCREMENT PRIMARY KEY,

    owner_id BIGINT NOT NULL,

    title VARCHAR(150) NOT NULL,

    currency VARCHAR(10) NOT NULL DEFAULT 'MAD',

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_budget_owner
        FOREIGN KEY (owner_id)
        REFERENCES accounts(account_id)
        ON DELETE CASCADE
);

-- =====================================================
-- CATEGORIES
-- =====================================================

CREATE TABLE categories (

    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,

    budget_id BIGINT NOT NULL,

    title VARCHAR(100) NOT NULL,

    icon VARCHAR(255),

    tx_type ENUM('INCOME','EXPENSE') NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_category_budget
        FOREIGN KEY (budget_id)
        REFERENCES budgets(budget_id)
        ON DELETE CASCADE
);

-- =====================================================
-- TRANSACTIONS
-- =====================================================

CREATE TABLE transactions (

    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,

    budget_id BIGINT NOT NULL,

    category_id BIGINT NOT NULL,

    amount DECIMAL(12,2) NOT NULL,

    note VARCHAR(255),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_transaction_budget
        FOREIGN KEY (budget_id)
        REFERENCES budgets(budget_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_transaction_category
        FOREIGN KEY (category_id)
        REFERENCES categories(category_id)
        ON DELETE CASCADE
);

-- =====================================================
-- INDEXES
-- =====================================================

CREATE INDEX idx_budget_owner
ON budgets(owner_id);

CREATE INDEX idx_category_budget
ON categories(budget_id);

CREATE INDEX idx_category_type
ON categories(tx_type);

CREATE INDEX idx_transaction_budget
ON transactions(budget_id);

CREATE INDEX idx_transaction_category
ON transactions(category_id);