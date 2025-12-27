package com.oficina.inventario.domain.item;

import com.oficina.inventario.domain.categoria.CategoriaId;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    Item salvar(Item item);

    Optional<Item> buscarPorId(ItemId id);

    Optional<Item> buscarPorCodigo(CodigoItem codigo);

    List<Item> buscarTodos();

    List<Item> buscarPorCategoria(CategoriaId categoriaId);

    List<Item> buscarAtivos();

    List<Item> buscarComEstoqueBaixo();

    boolean existePorCodigo(CodigoItem codigo);

    void excluir(ItemId id);
}
