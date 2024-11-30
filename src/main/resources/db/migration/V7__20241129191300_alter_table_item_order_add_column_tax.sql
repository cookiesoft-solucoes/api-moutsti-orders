-- Adicionar coluna tax_id na tabela item_order
ALTER TABLE item_order
    ADD COLUMN tax_id BIGINT;

-- Configurar chave estrangeira tax_id com a tabela tax
ALTER TABLE item_order
    ADD CONSTRAINT fk_item_order_tax
        FOREIGN KEY (tax_id) REFERENCES tax(id)
            ON DELETE SET NULL;

-- Adicionar um comentário para documentação
COMMENT ON COLUMN item_order.tax_id IS 'Referência ao imposto aplicado no item do pedido.';