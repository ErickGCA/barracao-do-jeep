package com.oficina.inventario.domain.item;

import com.oficina.inventario.domain.shared.ValueObject;

import java.util.Objects;

public final class CodigoItem extends ValueObject {

    private final String valor;

    private CodigoItem(String valor) {
        validarNaoVazio(valor, "Codigo");
        validarTamanho(valor, 3, 20, "Codigo");
        this.valor = valor.toUpperCase().trim();
    }

    public static CodigoItem of(String valor) {
        return new CodigoItem(valor);
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodigoItem)) return false;
        CodigoItem that = (CodigoItem) o;
        return Objects.equals(valor, that.valor);
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
