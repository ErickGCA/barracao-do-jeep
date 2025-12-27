package com.oficina.inventario.application.categoria.usecase;

import com.oficina.inventario.application.shared.UseCase;
import com.oficina.inventario.domain.categoria.Categoria;
import com.oficina.inventario.domain.categoria.CategoriaId;
import com.oficina.inventario.domain.categoria.CategoriaRepository;

public class DesativarCategoriaUseCase implements UseCase<String, Void> {

    private final CategoriaRepository categoriaRepository;

    public DesativarCategoriaUseCase(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Void executar(String id) {
        CategoriaId categoriaId = CategoriaId.of(id);

        Categoria categoria = categoriaRepository.buscarPorId(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("Categoria nao encontrada"));

        categoria.desativar();
        categoriaRepository.salvar(categoria);

        return null;
    }
}
