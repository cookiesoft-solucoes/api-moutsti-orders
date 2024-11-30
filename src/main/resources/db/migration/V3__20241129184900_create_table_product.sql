-- Criação da Sequência seq_product_id
CREATE SEQUENCE IF NOT EXISTS seq_product_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Criação da tabela Product
CREATE TABLE IF NOT EXISTS product (
   id BIGINT NOT NULL DEFAULT nextval('seq_product_id') PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   price NUMERIC(10, 2) NOT NULL,
   quantity INT NOT NULL,
   category_id BIGINT NOT NULL REFERENCES category(id),
   deleted BOOLEAN DEFAULT FALSE
);