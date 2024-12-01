-- Criação da Replica da tabela Categoria
CREATE TABLE IF NOT EXISTS rep_category (
       id BIGINT NOT NULL PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       description TEXT,
       deleted BOOLEAN DEFAULT FALSE
);