package com.oficina.inventario.application.item.dto;

public record AtualizarItemInput(
        String id,
        String codigo,
        String nome,
        String descricao,
        String unidadeMedida,
        Integer quantidadeMinima,
        String localizacao,
        String categoriaId
) {}
