-- Criação da Sequência seq_product_id
CREATE SEQUENCE IF NOT EXISTS seq_order_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Criação da tabela Order
CREATE TABLE IF NOT EXISTS "orders" (
   id BIGINT NOT NULL DEFAULT nextval('seq_order_id') PRIMARY KEY,
   code VARCHAR(50) NOT NULL,
   quantity INT NOT NULL,
   date TIMESTAMP NOT NULL,
   total NUMERIC(10, 2) NOT NULL,
   total_tax NUMERIC(10, 2) DEFAULT 0 NOT NULL,
   client_id BIGINT NOT NULL REFERENCES "rep_user"(id),
   status VARCHAR(50) DEFAULT 'CREATED' NOT NULL,
   deleted BOOLEAN DEFAULT FALSE
);