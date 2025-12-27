package com.oficina.inventario.domain.item;

import com.oficina.inventario.domain.shared.ValueObject;

import java.util.Objects;
import java.util.UUID;

public final class ItemId extends ValueObject {

    private final String valor;

    private ItemId(String valor) {
        validarNaoVazio(valor, "ItemId");
        this.valor = valor;
    }

    public static ItemId of(String valor) {
        return new ItemId(valor);
    }

    public static ItemId gerar() {
        return new ItemId(UUID.randomUUID().toString());
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemId)) return false;
        ItemId itemId = (ItemId) o;
        return Objects.equals(valor, itemId.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}
