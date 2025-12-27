package com.oficina.inventario.application.categoria.dto;

public record AtualizarCategoriaInput(
        String id,
        String nome,
        String descricao
) {}
