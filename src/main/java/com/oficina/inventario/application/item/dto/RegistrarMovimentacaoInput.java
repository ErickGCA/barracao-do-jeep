package com.oficina.inventario.application.item.dto;

public record RegistrarMovimentacaoInput(
        String itemId,
        Integer quantidade,
        String responsavel,
        String observacao
) {}
