package com.oficina.inventario.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDTO {

    @NotBlank(message = "Código é obrigatório")
    @Size(min = 3, max = 20, message = "Código deve ter entre 3 e 20 caracteres")
    private String codigo;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @NotBlank(message = "Unidade de medida é obrigatória")
    @Size(max = 10, message = "Unidade de medida deve ter no máximo 10 caracteres")
    private String unidadeMedida;

    @Min(value = 0, message = "Quantidade mínima não pode ser negativa")
    private Integer quantidadeMinima = 0;

    @Size(max = 200, message = "Localização deve ter no máximo 200 caracteres")
    private String localizacao;

    @NotNull(message = "Categoria é obrigatória")
    private Long categoriaId;
}
