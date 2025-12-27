package com.oficina.inventario.presentation.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDTO {

    @NotBlank(message = "Codigo e obrigatorio")
    @Size(min = 3, max = 20, message = "Codigo deve ter entre 3 e 20 caracteres")
    private String codigo;

    @NotBlank(message = "Nome e obrigatorio")
    @Size(min = 3, max = 200, message = "Nome deve ter entre 3 e 200 caracteres")
    private String nome;

    @Size(max = 500, message = "Descricao deve ter no maximo 500 caracteres")
    private String descricao;

    @NotBlank(message = "Unidade de medida e obrigatoria")
    private String unidadeMedida;

    @Min(value = 0, message = "Quantidade minima nao pode ser negativa")
    private Integer quantidadeMinima = 0;

    @Size(max = 200, message = "Localizacao deve ter no maximo 200 caracteres")
    private String localizacao;

    @NotBlank(message = "Categoria e obrigatoria")
    private String categoriaId;
}
