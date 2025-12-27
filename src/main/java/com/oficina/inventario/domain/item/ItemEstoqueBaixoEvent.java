package com.oficina.inventario.domain.item;

import com.oficina.inventario.domain.shared.DomainEvent;

import java.time.LocalDateTime;

public class ItemEstoqueBaixoEvent implements DomainEvent {

    private final ItemId itemId;
    private final Quantidade quantidadeAtual;
    private final LocalDateTime ocorridoEm;

    public ItemEstoqueBaixoEvent(ItemId itemId, Quantidade quantidadeAtual, LocalDateTime ocorridoEm) {
        this.itemId = itemId;
        this.quantidadeAtual = quantidadeAtual;
        this.ocorridoEm = ocorridoEm;
    }

    public ItemId getItemId() {
        return itemId;
    }

    public Quantidade getQuantidadeAtual() {
        return quantidadeAtual;
    }

    @Override
    public LocalDateTime ocorridoEm() {
        return ocorridoEm;
    }
}
