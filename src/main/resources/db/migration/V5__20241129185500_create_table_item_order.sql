-- Criação da Sequência seq_item_order_id
CREATE SEQUENCE IF NOT EXISTS seq_item_order_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Criação da tabela ItemOrder
CREATE TABLE IF NOT EXISTS item_order (
  id BIGINT NOT NULL DEFAULT nextval('seq_order_id') PRIMARY KEY,
  quantity INT NOT NULL,
  price NUMERIC(10, 2) NOT NULL,
  order_id BIGINT NOT NULL REFERENCES "orders"(id) ON DELETE CASCADE,
  product_id BIGINT NOT NULL REFERENCES rep_product(id),
  deleted BOOLEAN DEFAULT FALSE
);