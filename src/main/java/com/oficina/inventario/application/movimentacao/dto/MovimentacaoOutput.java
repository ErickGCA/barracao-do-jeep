package com.oficina.inventario.application.movimentacao.dto;

import com.oficina.inventario.domain.movimentacao.Movimentacao;
import com.oficina.inventario.domain.movimentacao.TipoMovimentacao;

import java.time.LocalDateTime;

public record MovimentacaoOutput(
        String id,
        String itemId,
        TipoMovimentacao tipo,
        Integer quantidade,
        Integer quantidadeAnterior,
        Integer quantidadeNova,
        String responsavel,
        String observacao,
        LocalDateTime dataMovimentacao,
        LocalDateTime createdAt
) {
    public static MovimentacaoOutput from(Movimentacao movimentacao) {
        return new MovimentacaoOutput(
                movimentacao.getId().getValor(),
                movimentacao.getItemId().getValor(),
                movimentacao.getTipo(),
                movimentacao.getQuantidade().getValor(),
                movimentacao.getQuantidadeAnterior().getValor(),
                movimentacao.getQuantidadeNova().getValor(),
                movimentacao.getResponsavel().getValor(),
                movimentacao.getObservacao(),
                movimentacao.getDataMovimentacao(),
                movimentacao.getCreatedAt()
        );
    }
}
