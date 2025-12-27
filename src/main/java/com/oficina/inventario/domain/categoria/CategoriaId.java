package com.oficina.inventario.domain.categoria;

import com.oficina.inventario.domain.shared.ValueObject;

import java.util.Objects;

public final class CategoriaId extends ValueObject {

    private final Long valor;

    private CategoriaId(Long valor) {
        if (valor == null) {
            throw new IllegalArgumentException("CategoriaId nao pode ser nulo");
        }
        this.valor = valor;
    }

    public static CategoriaId of(Long valor) {
        return new CategoriaId(valor);
    }

    public Long getValor() {
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
        return valor.toString();
    }
}
