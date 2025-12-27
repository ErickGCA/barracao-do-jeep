package com.oficina.inventario.dto;

import com.oficina.inventario.entity.Movimentacao;
import com.oficina.inventario.enums.TipoMovimentacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoResponseDTO {

    private Long id;
    private ItemResponseDTO item;
    private TipoMovimentacao tipoMovimentacao;
    private Integer quantidade;
    private Integer quantidadeAnterior;
    private Integer quantidadeNova;
    private String observacao;
    private String responsavel;
    private LocalDateTime dataMovimentacao;

    public static MovimentacaoResponseDTO fromEntity(Movimentacao movimentacao) {
        return new MovimentacaoResponseDTO(
                movimentacao.getId(),
                ItemResponseDTO.fromEntity(movimentacao.getItem()),
                movimentacao.getTipoMovimentacao(),
                movimentacao.getQuantidade(),
                movimentacao.getQuantidadeAnterior(),
                movimentacao.getQuantidadeNova(),
                movimentacao.getObservacao(),
                movimentacao.getResponsavel(),
                movimentacao.getDataMovimentacao()
        );
    }
}
