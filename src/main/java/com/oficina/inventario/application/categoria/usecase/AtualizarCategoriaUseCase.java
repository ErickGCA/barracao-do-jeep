package com.oficina.inventario.application.categoria.usecase;

import com.oficina.inventario.application.categoria.dto.AtualizarCategoriaInput;
import com.oficina.inventario.application.categoria.dto.CategoriaOutput;
import com.oficina.inventario.application.shared.UseCase;
import com.oficina.inventario.domain.categoria.Categoria;
import com.oficina.inventario.domain.categoria.CategoriaId;
import com.oficina.inventario.domain.categoria.CategoriaRepository;
import com.oficina.inventario.domain.categoria.NomeCategoria;

public class AtualizarCategoriaUseCase implements UseCase<AtualizarCategoriaInput, CategoriaOutput> {

    private final CategoriaRepository categoriaRepository;

    public AtualizarCategoriaUseCase(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public CategoriaOutput executar(AtualizarCategoriaInput input) {
        CategoriaId id = CategoriaId.of(input.id());
        NomeCategoria nome = NomeCategoria.of(input.nome());

        Categoria categoria = categoriaRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria nao encontrada"));

        categoria.atualizar(nome, input.descricao());
        Categoria categoriaAtualizada = categoriaRepository.salvar(categoria);

        return CategoriaOutput.from(categoriaAtualizada);
    }
}
