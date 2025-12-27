package com.oficina.inventario.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDTO {

    private String id;
    private String codigo;
    private String nome;
    private String descricao;
    private String unidadeMedida;
    private Integer quantidadeAtual;
    private Integer quantidadeMinima;
    private String localizacao;
    private String categoriaId;
    private Boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
