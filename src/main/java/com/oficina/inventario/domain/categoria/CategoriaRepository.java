package com.oficina.inventario.domain.categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository {

    Categoria salvar(Categoria categoria);

    Optional<Categoria> buscarPorId(CategoriaId id);

    List<Categoria> buscarTodas();

    List<Categoria> buscarAtivas();

    boolean existePorNome(NomeCategoria nome);

    void excluir(CategoriaId id);
}
