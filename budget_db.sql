CREATE DATABASE budget_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE budget_db;

CREATE TABLE users(
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    app_access BOOLEAN DEFAULT TRUE
);

CREATE TABLE accounts(
    account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_name VARCHAR(100) NOT NULL,
    user_id BIGINT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY(user_id)
     REFERENCES users(user_id)
);

CREATE TABLE categories(
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY(user_id)
       REFERENCES users(user_id)
);

CREATE TABLE transactions(
     transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
     account_id BIGINT NOT NULL,
     category_id BIGINT NOT NULL,
     transaction_type ENUM('INCOME','EXPENSE') NOT NULL,
     amount DECIMAL(10,2) NOT NULL,
     description VARCHAR(255),
     transaction_date DATE NOT NULL,
     cleared BOOLEAN DEFAULT FALSE,
     FOREIGN KEY(account_id)
         REFERENCES accounts(account_id),
     FOREIGN KEY(category_id)
         REFERENCES categories(category_id)
);

CREATE TABLE budgets(
    budget_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    budget_month DATE NOT NULL,
    FOREIGN KEY(category_id)
        REFERENCES categories(category_id)
);