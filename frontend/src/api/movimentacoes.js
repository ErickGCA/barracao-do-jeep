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
    api.post(`/itens/${data.itemId}/entrada`, {
      quantidade: data.quantidade,
      responsavel: data.responsavel,
      observacao: data.observacao
    }),

  // Registrar saida
  registrarSaida: (data) =>
    api.post(`/itens/${data.itemId}/saida`, {
      quantidade: data.quantidade,
      responsavel: data.responsavel,
      observacao: data.observacao
    })
};
