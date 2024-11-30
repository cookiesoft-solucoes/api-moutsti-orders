-- Criação da Sequência seq_users_id
CREATE SEQUENCE IF NOT EXISTS seq_users_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Criação da tabela Users
CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL DEFAULT nextval('seq_users_id') PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    idAuthentication VARCHAR(255) NOT NULL,
    deleted BOOLEAN DEFAULT FALSE
);