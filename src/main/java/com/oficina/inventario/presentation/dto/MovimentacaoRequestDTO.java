package com.oficina.inventario.presentation.dto;

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

    @NotNull(message = "Quantidade e obrigatoria")
    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    private Integer quantidade;

    @NotBlank(message = "Responsavel e obrigatorio")
    @Size(min = 3, max = 200, message = "Responsavel deve ter entre 3 e 200 caracteres")
    private String responsavel;

    @Size(max = 500, message = "Observacao deve ter no maximo 500 caracteres")
    private String observacao;
}
