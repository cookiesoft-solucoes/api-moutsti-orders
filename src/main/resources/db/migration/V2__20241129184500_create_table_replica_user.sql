-- Criação da Replica da tabela Users
CREATE TABLE IF NOT EXISTS rep_user (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    deleted BOOLEAN DEFAULT FALSE
);