package com.oficina.inventario.domain.movimentacao;

import com.oficina.inventario.domain.shared.ValueObject;

import java.util.Objects;

public final class MovimentacaoId extends ValueObject {

    private final Long valor;

    private MovimentacaoId(Long valor) {
        if (valor == null) {
            throw new IllegalArgumentException("MovimentacaoId nao pode ser nulo");
        }
        this.valor = valor;
    }

    public static MovimentacaoId of(Long valor) {
        return new MovimentacaoId(valor);
    }

    public Long getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovimentacaoId)) return false;
        MovimentacaoId that = (MovimentacaoId) o;
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
