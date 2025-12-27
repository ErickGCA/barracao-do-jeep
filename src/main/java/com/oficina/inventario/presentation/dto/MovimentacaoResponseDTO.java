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
    private String tipo;
    private Integer quantidade;
    private String responsavel;
    private String observacao;
    private LocalDateTime dataMovimentacao;
    private LocalDateTime createdAt;
}
