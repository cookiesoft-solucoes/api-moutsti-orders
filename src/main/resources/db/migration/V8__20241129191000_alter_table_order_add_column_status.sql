-- Adicionar coluna status na tabela Order
ALTER TABLE "order"
    ADD COLUMN status VARCHAR(50) DEFAULT 'CREATED' NOT NULL;