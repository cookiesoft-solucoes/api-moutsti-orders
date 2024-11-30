-- Adicionar coluna status na tabela tax
ALTER TABLE "tax"
    ADD COLUMN deleted BOOLEAN DEFAULT FALSE NOT NULL;