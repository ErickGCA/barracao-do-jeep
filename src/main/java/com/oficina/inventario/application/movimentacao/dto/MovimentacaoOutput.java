package com.oficina.inventario.application.movimentacao.dto;

import com.oficina.inventario.domain.movimentacao.Movimentacao;
import com.oficina.inventario.domain.movimentacao.TipoMovimentacao;

import java.time.LocalDateTime;

public record MovimentacaoOutput(
        String id,
        String itemId,
        TipoMovimentacao tipo,
        Integer quantidade,
        String responsavel,
        String observacao,
        LocalDateTime dataMovimentacao,
        LocalDateTime createdAt
) {
    public static MovimentacaoOutput from(Movimentacao movimentacao) {
        return new MovimentacaoOutput(
                movimentacao.getId().valor(),
                movimentacao.getItemId().valor(),
                movimentacao.getTipo(),
                movimentacao.getQuantidade().valor(),
                movimentacao.getResponsavel().valor(),
                movimentacao.getObservacao(),
                movimentacao.getDataMovimentacao(),
                movimentacao.getCreatedAt()
        );
    }
}
