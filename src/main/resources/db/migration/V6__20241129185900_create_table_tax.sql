-- Criação da Sequência seq_tax_id
CREATE SEQUENCE IF NOT EXISTS seq_tax_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Criação da tabela Tax
CREATE TABLE IF NOT EXISTS tax (
   id BIGINT NOT NULL DEFAULT nextval('seq_tax_id') PRIMARY KEY,
   tax_type VARCHAR(100) NOT NULL, -- Tipo do imposto (ex.: IVA, ICMS, ISS)
   category_id BIGINT NOT NULL REFERENCES rep_category(id), -- Relacionamento com a categoria
   rate NUMERIC(5, 2) NOT NULL CHECK (rate >= 0), -- Alíquota, com validação para não ser negativa
   description TEXT, -- Descrição opcional do imposto
   deleted BOOLEAN DEFAULT FALSE
);