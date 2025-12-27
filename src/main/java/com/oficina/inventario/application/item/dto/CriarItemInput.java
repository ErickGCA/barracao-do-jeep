package com.oficina.inventario.application.item.dto;

public record CriarItemInput(
        String codigo,
        String nome,
        String descricao,
        String unidadeMedida,
        Integer quantidadeMinima,
        String localizacao,
        String categoriaId
) {}
