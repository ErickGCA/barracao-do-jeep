package com.oficina.inventario.controller;

import com.oficina.inventario.dto.ItemRequestDTO;
import com.oficina.inventario.dto.ItemResponseDTO;
import com.oficina.inventario.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itens")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> listarTodos(
            @RequestParam(required = false, defaultValue = "false") boolean apenasAtivos) {

        List<ItemResponseDTO> itens = apenasAtivos
                ? itemService.listarAtivos()
                : itemService.listarTodos();

        return ResponseEntity.ok(itens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> buscarPorId(@PathVariable Long id) {
        ItemResponseDTO item = itemService.buscarPorId(id);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<ItemResponseDTO> buscarPorCodigo(@PathVariable String codigo) {
        ItemResponseDTO item = itemService.buscarPorCodigo(codigo);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ItemResponseDTO>> listarPorCategoria(@PathVariable Long categoriaId) {
        List<ItemResponseDTO> itens = itemService.listarPorCategoria(categoriaId);
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/estoque-baixo")
    public ResponseEntity<List<ItemResponseDTO>> listarEstoqueBaixo() {
        List<ItemResponseDTO> itens = itemService.listarItensComEstoqueBaixo();
        return ResponseEntity.ok(itens);
    }

    @PostMapping
    public ResponseEntity<ItemResponseDTO> criar(@Valid @RequestBody ItemRequestDTO requestDTO) {
        ItemResponseDTO item = itemService.criar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ItemRequestDTO requestDTO) {

        ItemResponseDTO item = itemService.atualizar(id, requestDTO);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        itemService.desativar(id);
        return ResponseEntity.noContent().build();
    }
}
