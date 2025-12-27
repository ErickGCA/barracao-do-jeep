package com.oficina.inventario.domain.shared;

/**
 * Classe base para Value Objects (DDD)
 * Value Objects sao imutaveis e definidos por seus atributos, nao por identidade
 */
public abstract class ValueObject {

    /**
     * Value Objects devem implementar equals() baseado em seus atributos
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * Value Objects devem implementar hashCode() consistente com equals()
     */
    @Override
    public abstract int hashCode();

    /**
     * Validacao comum para strings nao vazias
     */
    protected void validarNaoVazio(String valor, String nomeCampo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(nomeCampo + " nao pode ser vazio");
        }
    }

    /**
     * Validacao comum para tamanho de string
     */
    protected void validarTamanho(String valor, int min, int max, String nomeCampo) {
        if (valor.length() < min || valor.length() > max) {
            throw new IllegalArgumentException(
                String.format("%s deve ter entre %d e %d caracteres", nomeCampo, min, max)
            );
        }
    }

    /**
     * Validacao comum para valores positivos
     */
    protected void validarPositivo(Number valor, String nomeCampo) {
        if (valor.doubleValue() < 0) {
            throw new IllegalArgumentException(nomeCampo + " deve ser positivo");
        }
    }
}
