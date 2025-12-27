import api from './axios';

export const categoriasAPI = {
  // Listar todas
  listarTodas: (apenasAtivas = false) =>
    api.get(`/categorias?apenasAtivas=${apenasAtivas}`),

  // Buscar por ID
  buscarPorId: (id) =>
    api.get(`/categorias/${id}`),

  // Criar nova
  criar: (data) =>
    api.post('/categorias', data),

  // Atualizar
  atualizar: (id, data) =>
    api.put(`/categorias/${id}`, data),

  // Desativar
  desativar: (id) =>
    api.delete(`/categorias/${id}`)
};
