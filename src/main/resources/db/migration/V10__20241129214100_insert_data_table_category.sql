-- Inserção de categorias na tabela category
INSERT INTO category (id, name, description, created_at, created_by, updated_at, updated_by, deleted)
VALUES
    (1, 'Eletrônicos', 'Dispositivos eletrônicos como TVs, celulares e laptops.', NOW(), 'system', NOW(), 'system', FALSE),
    (2, 'Eletrodomésticos', 'Aparelhos para uso doméstico, como geladeiras e micro-ondas.', NOW(), 'system', NOW(), 'system', FALSE),
    (3, 'Móveis', 'Móveis para casa e escritório, como mesas e cadeiras.', NOW(), 'system', NOW(), 'system', FALSE),
    (4, 'Livros', 'Livros de diferentes gêneros e autores.', NOW(), 'system', NOW(), 'system', FALSE),
    (5, 'Roupas', 'Vestuário para homens, mulheres e crianças.', NOW(), 'system', NOW(), 'system', FALSE),
    (6, 'Calçados', 'Calçados de todos os tipos e tamanhos.', NOW(), 'system', NOW(), 'system', FALSE),
    (7, 'Alimentos', 'Produtos alimentícios e bebidas.', NOW(), 'system', NOW(), 'system', FALSE),
    (8, 'Beleza e Saúde', 'Produtos para cuidados pessoais e saúde.', NOW(), 'system', NOW(), 'system', FALSE);
