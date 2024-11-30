-- Adicionar coluna total_tax na tabela Order
ALTER TABLE "order"
    ADD COLUMN total_tax NUMERIC(10, 2) DEFAULT 0 NOT NULL;