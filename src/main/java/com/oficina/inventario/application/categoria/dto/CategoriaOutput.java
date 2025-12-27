package com.oficina.inventario.application.categoria.dto;

import com.oficina.inventario.domain.categoria.Categoria;

import java.time.LocalDateTime;

public record CategoriaOutput(
        String id,
        String nome,
        String descricao,
        boolean ativo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CategoriaOutput from(Categoria categoria) {
        return new CategoriaOutput(
                categoria.getId().valor(),
                categoria.getNome().valor(),
                categoria.getDescricao(),
                categoria.isAtivo(),
                categoria.getCreatedAt(),
                categoria.getUpdatedAt()
        );
    }
}
