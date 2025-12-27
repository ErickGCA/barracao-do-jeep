-- Dados iniciais para testes
-- Este arquivo e executado automaticamente pelo Spring Boot ao iniciar

-- Inserir categorias
INSERT INTO categorias (id, nome, descricao, ativo, created_at, updated_at) VALUES
('550e8400-e29b-41d4-a716-446655440001', 'Ferramentas', 'Ferramentas manuais e eletricas', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('550e8400-e29b-41d4-a716-446655440002', 'Pecas', 'Pecas automotivas diversas', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('550e8400-e29b-41d4-a716-446655440003', 'Consumiveis', 'Itens de consumo regular', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('550e8400-e29b-41d4-a716-446655440004', 'Parafusos', 'Parafusos, porcas e arruelas', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Inserir itens de exemplo
INSERT INTO itens (id, codigo, nome, descricao, unidade_medida, quantidade_atual, quantidade_minima, localizacao, categoria_id, ativo, created_at, updated_at) VALUES
('650e8400-e29b-41d4-a716-446655440001', 'PARA-001', 'Parafuso M8x50', 'Parafuso sextavado M8 comprimento 50mm', 'UN', 0, 100, 'Prateleira A3', '550e8400-e29b-41d4-a716-446655440004', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('650e8400-e29b-41d4-a716-446655440002', 'PARA-002', 'Parafuso M10x60', 'Parafuso sextavado M10 comprimento 60mm', 'UN', 0, 80, 'Prateleira A3', '550e8400-e29b-41d4-a716-446655440004', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('650e8400-e29b-41d4-a716-446655440003', 'FILT-001', 'Filtro de Oleo', 'Filtro de oleo motor universal', 'UN', 0, 20, 'Armario B1', '550e8400-e29b-41d4-a716-446655440002', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('650e8400-e29b-41d4-a716-446655440004', 'FILT-002', 'Filtro de Ar', 'Filtro de ar motor universal', 'UN', 0, 15, 'Armario B1', '550e8400-e29b-41d4-a716-446655440002', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('650e8400-e29b-41d4-a716-446655440005', 'OLEO-001', 'Oleo Motor 5W30', 'Oleo sintetico 5W30 para motor', 'L', 0, 50, 'Deposito C', '550e8400-e29b-41d4-a716-446655440003', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('650e8400-e29b-41d4-a716-446655440006', 'OLEO-002', 'Oleo Motor 10W40', 'Oleo semissintetico 10W40 para motor', 'L', 0, 40, 'Deposito C', '550e8400-e29b-41d4-a716-446655440003', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('650e8400-e29b-41d4-a716-446655440007', 'FERR-001', 'Chave de Fenda 3/16', 'Chave de fenda magnetica ponta 3/16', 'UN', 0, 5, 'Painel de ferramentas', '550e8400-e29b-41d4-a716-446655440001', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('650e8400-e29b-41d4-a716-446655440008', 'FERR-002', 'Chave Phillips #2', 'Chave Phillips #2 cabo emborrachado', 'UN', 0, 5, 'Painel de ferramentas', '550e8400-e29b-41d4-a716-446655440001', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('650e8400-e29b-41d4-a716-446655440009', 'FERR-003', 'Alicate Universal 8"', 'Alicate universal 8 polegadas', 'UN', 0, 3, 'Painel de ferramentas', '550e8400-e29b-41d4-a716-446655440001', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Nota: As movimentações devem ser criadas via API para garantir
-- que a lógica de negócio (atualização de estoque) seja executada corretamente
