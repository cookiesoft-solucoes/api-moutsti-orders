-- Criação da Replica da tabela Produto
CREATE TABLE IF NOT EXISTS rep_product (
   id BIGINT NOT NULL PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   price NUMERIC(10, 2) NOT NULL,
   quantity INT NOT NULL,
   category_id BIGINT NOT NULL REFERENCES rep_category(id),
   deleted BOOLEAN DEFAULT FALSE
);