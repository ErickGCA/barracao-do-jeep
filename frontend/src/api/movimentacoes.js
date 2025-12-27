import api from './axios';

export const movimentacoesAPI = {
  // Listar todas
  listarTodas: () =>
    api.get('/movimentacoes'),

  // Buscar por ID
  buscarPorId: (id) =>
    api.get(`/movimentacoes/${id}`),

  // Listar por item
  listarPorItem: (itemId) =>
    api.get(`/movimentacoes/item/${itemId}`),

  // Registrar entrada
  registrarEntrada: (data) =>
    api.post('/movimentacoes/entrada', data),

  // Registrar saida
  registrarSaida: (data) =>
    api.post('/movimentacoes/saida', data)
};
