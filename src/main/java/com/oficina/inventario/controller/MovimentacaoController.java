package com.oficina.inventario.controller;

import com.oficina.inventario.dto.MovimentacaoRequestDTO;
import com.oficina.inventario.dto.MovimentacaoResponseDTO;
import com.oficina.inventario.service.MovimentacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    @GetMapping
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarTodas() {
        List<MovimentacaoResponseDTO> movimentacoes = movimentacaoService.listarTodas();
        return ResponseEntity.ok(movimentacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimentacaoResponseDTO> buscarPorId(@PathVariable Long id) {
        MovimentacaoResponseDTO movimentacao = movimentacaoService.buscarPorId(id);
        return ResponseEntity.ok(movimentacao);
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarPorItem(@PathVariable Long itemId) {
        List<MovimentacaoResponseDTO> movimentacoes = movimentacaoService.listarPorItem(itemId);
        return ResponseEntity.ok(movimentacoes);
    }

    @PostMapping("/entrada")
    public ResponseEntity<MovimentacaoResponseDTO> registrarEntrada(
            @Valid @RequestBody MovimentacaoRequestDTO requestDTO) {

        MovimentacaoResponseDTO movimentacao = movimentacaoService.registrarEntrada(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimentacao);
    }

    @PostMapping("/saida")
    public ResponseEntity<MovimentacaoResponseDTO> registrarSaida(
            @Valid @RequestBody MovimentacaoRequestDTO requestDTO) {

        MovimentacaoResponseDTO movimentacao = movimentacaoService.registrarSaida(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimentacao);
    }
}
