package com.oficina.inventario.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoResponseDTO {

    private String id;
    private String itemId;
    private String tipoMovimentacao;
    private Integer quantidade;
    private Integer quantidadeAnterior;
    private Integer quantidadeNova;
    private String responsavel;
    private String observacao;
    private LocalDateTime dataMovimentacao;
    private LocalDateTime createdAt;
    private ItemInfo item;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemInfo {
        private String id;
        private String codigo;
        private String nome;
    }
}
