package com.oficina.inventario.domain.categoria;

import com.oficina.inventario.domain.shared.ValueObject;

import java.util.Objects;

public final class NomeCategoria extends ValueObject {

    private final String valor;

    private NomeCategoria(String valor) {
        validarNaoVazio(valor, "Nome da categoria");
        validarTamanho(valor, 3, 100, "Nome da categoria");
        this.valor = valor.trim();
    }

    public static NomeCategoria of(String valor) {
        return new NomeCategoria(valor);
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NomeCategoria)) return false;
        NomeCategoria that = (NomeCategoria) o;
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
