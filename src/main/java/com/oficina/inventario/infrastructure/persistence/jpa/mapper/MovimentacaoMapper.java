package com.oficina.inventario.infrastructure.persistence.jpa.mapper;

import com.oficina.inventario.domain.item.ItemId;
import com.oficina.inventario.domain.item.Quantidade;
import com.oficina.inventario.domain.movimentacao.Movimentacao;
import com.oficina.inventario.domain.movimentacao.MovimentacaoId;
import com.oficina.inventario.domain.movimentacao.Responsavel;
import com.oficina.inventario.domain.movimentacao.TipoMovimentacao;
import com.oficina.inventario.infrastructure.persistence.jpa.entity.MovimentacaoJpaEntity;

public class MovimentacaoMapper {

    public static MovimentacaoJpaEntity toJpaEntity(Movimentacao movimentacao) {
        MovimentacaoJpaEntity jpaEntity = new MovimentacaoJpaEntity();
        jpaEntity.setId(movimentacao.getId().getValor());
        jpaEntity.setItemId(movimentacao.getItemId().getValor());
        jpaEntity.setTipo(movimentacao.getTipo().name());
        jpaEntity.setQuantidade(movimentacao.getQuantidade().getValor());
        jpaEntity.setResponsavel(movimentacao.getResponsavel().getValor());
        jpaEntity.setObservacao(movimentacao.getObservacao());
        jpaEntity.setDataMovimentacao(movimentacao.getDataMovimentacao());
        jpaEntity.setCreatedAt(movimentacao.getCreatedAt());
        return jpaEntity;
    }

    public static Movimentacao toDomain(MovimentacaoJpaEntity jpaEntity) {
        TipoMovimentacao tipo = TipoMovimentacao.valueOf(jpaEntity.getTipo());

        Movimentacao movimentacao = tipo == TipoMovimentacao.ENTRADA
                ? Movimentacao.registrarEntrada(
                        ItemId.of(jpaEntity.getItemId()),
                        Quantidade.of(jpaEntity.getQuantidade()),
                        Responsavel.of(jpaEntity.getResponsavel()),
                        jpaEntity.getObservacao()
                )
                : Movimentacao.registrarSaida(
                        ItemId.of(jpaEntity.getItemId()),
                        Quantidade.of(jpaEntity.getQuantidade()),
                        Responsavel.of(jpaEntity.getResponsavel()),
                        jpaEntity.getObservacao()
                );

        movimentacao.setId(MovimentacaoId.of(jpaEntity.getId()));
        return movimentacao;
    }
}
