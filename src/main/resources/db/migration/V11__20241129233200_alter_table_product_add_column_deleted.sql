-- Adicionar coluna status na tabela tax
ALTER TABLE "product"
    ADD COLUMN deleted BOOLEAN DEFAULT FALSE NOT NULL;