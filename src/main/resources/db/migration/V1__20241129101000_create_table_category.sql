-- Criação da Sequência seq_category_id
CREATE SEQUENCE IF NOT EXISTS seq_category_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Criação da tabela Category
CREATE TABLE IF NOT EXISTS category (
       id BIGINT NOT NULL DEFAULT nextval('seq_category_id') PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       description TEXT,
       created_at TIMESTAMP,
       created_by VARCHAR(100),
       updated_at TIMESTAMP,
       updated_by VARCHAR(100),
       deleted BOOLEAN DEFAULT FALSE
);