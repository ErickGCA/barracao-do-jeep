package com.oficina.inventario.domain.categoria;

import com.oficina.inventario.domain.shared.ValueObject;

import java.util.Objects;
import java.util.UUID;

public final class CategoriaId extends ValueObject {

    private final String valor;

    private CategoriaId(String valor) {
        validarNaoVazio(valor, "CategoriaId");
        this.valor = valor;
    }

    public static CategoriaId of(String valor) {
        return new CategoriaId(valor);
    }

    public static CategoriaId gerar() {
        return new CategoriaId(UUID.randomUUID().toString());
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoriaId)) return false;
        CategoriaId that = (CategoriaId) o;
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
