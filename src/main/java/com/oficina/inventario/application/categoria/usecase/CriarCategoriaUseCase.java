package com.oficina.inventario.application.categoria.usecase;

import com.oficina.inventario.application.categoria.dto.CategoriaOutput;
import com.oficina.inventario.application.categoria.dto.CriarCategoriaInput;
import com.oficina.inventario.application.shared.UseCase;
import com.oficina.inventario.domain.categoria.Categoria;
import com.oficina.inventario.domain.categoria.CategoriaRepository;
import com.oficina.inventario.domain.categoria.NomeCategoria;

public class CriarCategoriaUseCase implements UseCase<CriarCategoriaInput, CategoriaOutput> {

    private final CategoriaRepository categoriaRepository;

    public CriarCategoriaUseCase(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public CategoriaOutput executar(CriarCategoriaInput input) {
        NomeCategoria nome = NomeCategoria.of(input.nome());

        if (categoriaRepository.existePorNome(nome)) {
            throw new IllegalArgumentException("Ja existe uma categoria com este nome");
        }

        Categoria categoria = Categoria.criar(nome, input.descricao());
        Categoria categoriaSalva = categoriaRepository.salvar(categoria);

        return CategoriaOutput.from(categoriaSalva);
    }
}
