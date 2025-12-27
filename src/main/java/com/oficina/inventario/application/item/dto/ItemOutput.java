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
                item.getId().getValor(),
                item.getCodigo().getValor(),
                item.getNome(),
                item.getDescricao(),
                item.getUnidadeMedida().name(),
                item.getQuantidadeAtual().getValor(),
                item.getQuantidadeMinima().getValor(),
                item.getLocalizacao(),
                item.getCategoriaId().getValor(),
                item.isAtivo(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }
}
