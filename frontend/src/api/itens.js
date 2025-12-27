import api from './axios';

export const itensAPI = {
  // Listar todos
  listarTodos: (apenasAtivos = false) =>
    api.get(`/itens?apenasAtivos=${apenasAtivos}`),

  // Buscar por ID
  buscarPorId: (id) =>
    api.get(`/itens/${id}`),

  // Buscar por codigo
  buscarPorCodigo: (codigo) =>
    api.get(`/itens/codigo/${codigo}`),

  // Listar por categoria
  listarPorCategoria: (categoriaId) =>
    api.get(`/itens/categoria/${categoriaId}`),

  // Listar estoque baixo
  listarEstoqueBaixo: () =>
    api.get('/itens/estoque-baixo'),

  // Criar novo
  criar: (data) =>
    api.post('/itens', data),

  // Atualizar
  atualizar: (id, data) =>
    api.put(`/itens/${id}`, data),

  // Desativar
  desativar: (id) =>
    api.delete(`/itens/${id}`)
};
