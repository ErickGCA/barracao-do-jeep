package com.oficina.inventario.application.categoria.usecase;

import com.oficina.inventario.application.categoria.dto.CategoriaOutput;
import com.oficina.inventario.application.shared.UseCase;
import com.oficina.inventario.domain.categoria.CategoriaRepository;

import java.util.List;
import java.util.stream.Collectors;

public class BuscarCategoriasUseCase implements UseCase<Void, List<CategoriaOutput>> {

    private final CategoriaRepository categoriaRepository;

    public BuscarCategoriasUseCase(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<CategoriaOutput> executar(Void input) {
        return categoriaRepository.buscarTodas()
                .stream()
                .map(CategoriaOutput::from)
                .collect(Collectors.toList());
    }
}
