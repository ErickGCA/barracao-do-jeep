package com.oficina.inventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoRequestDTO {

    @NotNull(message = "Item é obrigatório")
    private Long itemId;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    private Integer quantidade;

    @Size(max = 500, message = "Observação deve ter no máximo 500 caracteres")
    private String observacao;

    @NotBlank(message = "Responsável é obrigatório")
    @Size(min = 3, max = 100, message = "Responsável deve ter entre 3 e 100 caracteres")
    private String responsavel;
}
