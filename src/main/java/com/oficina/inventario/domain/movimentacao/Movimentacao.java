package com.oficina.inventario.domain.movimentacao;

import com.oficina.inventario.domain.item.ItemId;
import com.oficina.inventario.domain.item.Quantidade;
import com.oficina.inventario.domain.shared.AggregateRoot;

import java.time.LocalDateTime;

public class Movimentacao extends AggregateRoot<MovimentacaoId> {

    private MovimentacaoId id;
    private ItemId itemId;
    private TipoMovimentacao tipo;
    private Quantidade quantidade;
    private Responsavel responsavel;
    private String observacao;
    private LocalDateTime dataMovimentacao;
    private LocalDateTime createdAt;

    private Movimentacao() {}

    public static Movimentacao registrarEntrada(
            ItemId itemId,
            Quantidade quantidade,
            Responsavel responsavel,
            String observacao
    ) {
        validarQuantidade(quantidade);
        validarObservacao(observacao);

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.itemId = itemId;
        movimentacao.tipo = TipoMovimentacao.ENTRADA;
        movimentacao.quantidade = quantidade;
        movimentacao.responsavel = responsavel;
        movimentacao.observacao = observacao;
        movimentacao.dataMovimentacao = LocalDateTime.now();
        movimentacao.createdAt = LocalDateTime.now();

        return movimentacao;
    }

    public static Movimentacao registrarSaida(
            ItemId itemId,
            Quantidade quantidade,
            Responsavel responsavel,
            String observacao
    ) {
        validarQuantidade(quantidade);
        validarObservacao(observacao);

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.itemId = itemId;
        movimentacao.tipo = TipoMovimentacao.SAIDA;
        movimentacao.quantidade = quantidade;
        movimentacao.responsavel = responsavel;
        movimentacao.observacao = observacao;
        movimentacao.dataMovimentacao = LocalDateTime.now();
        movimentacao.createdAt = LocalDateTime.now();

        return movimentacao;
    }

    private static void validarQuantidade(Quantidade quantidade) {
        if (quantidade.menorOuIgual(Quantidade.zero())) {
            throw new IllegalArgumentException("Quantidade da movimentacao deve ser maior que zero");
        }
    }

    private static void validarObservacao(String observacao) {
        if (observacao != null && observacao.length() > 500) {
            throw new IllegalArgumentException("Observacao deve ter no maximo 500 caracteres");
        }
    }

    @Override
    public MovimentacaoId getId() {
        return id;
    }

    public void setId(MovimentacaoId id) {
        this.id = id;
    }

    public ItemId getItemId() {
        return itemId;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public Quantidade getQuantidade() {
        return quantidade;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public String getObservacao() {
        return observacao;
    }

    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
