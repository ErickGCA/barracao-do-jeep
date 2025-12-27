package com.oficina.inventario.domain.movimentacao;

import com.oficina.inventario.domain.shared.ValueObject;

import java.util.Objects;

public final class Responsavel extends ValueObject {

    private final String nome;

    private Responsavel(String nome) {
        validarNaoVazio(nome, "Responsavel");
        validarTamanho(nome, 3, 100, "Responsavel");
        this.nome = nome.trim();
    }

    public static Responsavel of(String nome) {
        return new Responsavel(nome);
    }

    public String getValor() {
        return nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Responsavel)) return false;
        Responsavel that = (Responsavel) o;
        return Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    @Override
    public String toString() {
        return nome;
    }
}
