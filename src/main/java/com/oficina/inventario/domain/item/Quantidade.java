package com.oficina.inventario.domain.item;

import com.oficina.inventario.domain.shared.ValueObject;

import java.util.Objects;

public final class Quantidade extends ValueObject {

    private final Integer valor;

    private Quantidade(Integer valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Quantidade nao pode ser nula");
        }
        validarPositivo(valor, "Quantidade");
        this.valor = valor;
    }

    public static Quantidade of(Integer valor) {
        return new Quantidade(valor);
    }

    public static Quantidade zero() {
        return new Quantidade(0);
    }

    public Quantidade somar(Quantidade outra) {
        return new Quantidade(this.valor + outra.valor);
    }

    public Quantidade subtrair(Quantidade outra) {
        int resultado = this.valor - outra.valor;
        if (resultado < 0) {
            throw new IllegalArgumentException("Quantidade nao pode ser negativa");
        }
        return new Quantidade(resultado);
    }

    public boolean maiorQue(Quantidade outra) {
        return this.valor > outra.valor;
    }

    public boolean menorQue(Quantidade outra) {
        return this.valor < outra.valor;
    }

    public boolean menorOuIgual(Quantidade outra) {
        return this.valor <= outra.valor;
    }

    public Integer getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quantidade)) return false;
        Quantidade that = (Quantidade) o;
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
