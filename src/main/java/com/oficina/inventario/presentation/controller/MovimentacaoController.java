package com.oficina.inventario.presentation.controller;

import com.oficina.inventario.application.movimentacao.dto.MovimentacaoOutput;
import com.oficina.inventario.application.movimentacao.usecase.BuscarMovimentacoesUseCase;
import com.oficina.inventario.presentation.dto.MovimentacaoResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movimentacoes")
public class MovimentacaoController {

    private final BuscarMovimentacoesUseCase buscarMovimentacoesUseCase;

    public MovimentacaoController(BuscarMovimentacoesUseCase buscarMovimentacoesUseCase) {
        this.buscarMovimentacoesUseCase = buscarMovimentacoesUseCase;
    }

    @GetMapping
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarTodas() {
        List<MovimentacaoOutput> movimentacoes = buscarMovimentacoesUseCase.executar(null);

        List<MovimentacaoResponseDTO> response = movimentacoes.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    private MovimentacaoResponseDTO toResponseDTO(MovimentacaoOutput output) {
        MovimentacaoResponseDTO dto = new MovimentacaoResponseDTO();
        dto.setId(output.id());
        dto.setItemId(output.itemId());
        dto.setTipo(output.tipo().name());
        dto.setQuantidade(output.quantidade());
        dto.setResponsavel(output.responsavel());
        dto.setObservacao(output.observacao());
        dto.setDataMovimentacao(output.dataMovimentacao());
        dto.setCreatedAt(output.createdAt());
        return dto;
    }
}
