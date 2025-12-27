package com.oficina.inventario.dto;

import com.oficina.inventario.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDTO {

    private Long id;
    private String codigo;
    private String nome;
    private String descricao;
    private String unidadeMedida;
    private Integer quantidadeAtual;
    private Integer quantidadeMinima;
    private String localizacao;
    private CategoriaResponseDTO categoria;
    private Boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ItemResponseDTO fromEntity(Item item) {
        return new ItemResponseDTO(
                item.getId(),
                item.getCodigo(),
                item.getNome(),
                item.getDescricao(),
                item.getUnidadeMedida(),
                item.getQuantidadeAtual(),
                item.getQuantidadeMinima(),
                item.getLocalizacao(),
                CategoriaResponseDTO.fromEntity(item.getCategoria()),
                item.getAtivo(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }
}
