package com.oficina.inventario.domain.item;

public enum UnidadeMedida {
    UN("Unidade"),
    KG("Quilograma"),
    L("Litro"),
    M("Metro"),
    PC("Peca");

    private final String descricao;

    UnidadeMedida(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static UnidadeMedida from(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Unidade de medida nao pode ser vazia");
        }
        try {
            return UnidadeMedida.valueOf(valor.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unidade de medida invalida: " + valor);
        }
    }
}
