package com.oficina.inventario.domain.movimentacao;

import com.oficina.inventario.domain.shared.ValueObject;

import java.util.Objects;
import java.util.UUID;

public final class MovimentacaoId extends ValueObject {

    private final String valor;

    private MovimentacaoId(String valor) {
        validarNaoVazio(valor, "MovimentacaoId");
        this.valor = valor;
    }

    public static MovimentacaoId of(String valor) {
        return new MovimentacaoId(valor);
    }

    public static MovimentacaoId gerar() {
        return new MovimentacaoId(UUID.randomUUID().toString());
    }

    public String getValor() {
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
        return valor;
    }
}
