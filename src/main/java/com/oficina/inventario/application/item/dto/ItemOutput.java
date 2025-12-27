package com.oficina.inventario.application.item.dto;

import com.oficina.inventario.domain.item.Item;

import java.time.LocalDateTime;

public record ItemOutput(
        String id,
        String codigo,
        String nome,
        String descricao,
        String unidadeMedida,
        Integer quantidadeAtual,
        Integer quantidadeMinima,
        String localizacao,
        String categoriaId,
        boolean ativo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ItemOutput from(Item item) {
        return new ItemOutput(
                item.getId().valor(),
                item.getCodigo().valor(),
                item.getNome(),
                item.getDescricao(),
                item.getUnidadeMedida().name(),
                item.getQuantidadeAtual().valor(),
                item.getQuantidadeMinima().valor(),
                item.getLocalizacao(),
                item.getCategoriaId().valor(),
                item.isAtivo(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }
}
