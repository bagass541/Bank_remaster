
-- Создание ENUM-типа "transaction_type"
CREATE TYPE transaction_type AS ENUM ('DEPOSIT', 'WITHDRAW', 'TRANSFER');

-- Создание таблицы "banks"
DROP TABLE IF EXISTS banks;
CREATE TABLE banks (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

-- Создание таблицы "users"
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    surname VARCHAR NOT NULL,
    name VARCHAR NOT NULL,
    middle_name VARCHAR NOT NULL
);

-- Создание таблицы "accounts"
DROP TABLE IF EXISTS accounts;
CREATE TABLE accounts (
    id SERIAL PRIMARY KEY,
    account_number VARCHAR NOT NULL,
    balance NUMERIC(15, 2) NOT NULL,
    user_id INT REFERENCES users(id),
    bank_id INT REFERENCES banks(id),
    opening_date DATE NOT NULL,
);

-- Создание таблицы "transactions"
DROP TABLE IF EXISTS transactions;
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    time TIMESTAMP NOT NULL,
    sender_account_id INT REFERENCES accounts(id),
    receiver_account_id INT REFERENCES accounts(id),
    amount NUMERIC(15, 2) NOT NULL,
    transaction_type transaction_type
);