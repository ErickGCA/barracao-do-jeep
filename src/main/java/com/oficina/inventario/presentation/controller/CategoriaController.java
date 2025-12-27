package com.oficina.inventario.presentation.controller;

import com.oficina.inventario.application.categoria.dto.AtualizarCategoriaInput;
import com.oficina.inventario.application.categoria.dto.CategoriaOutput;
import com.oficina.inventario.application.categoria.dto.CriarCategoriaInput;
import com.oficina.inventario.application.categoria.usecase.AtualizarCategoriaUseCase;
import com.oficina.inventario.application.categoria.usecase.BuscarCategoriasUseCase;
import com.oficina.inventario.application.categoria.usecase.CriarCategoriaUseCase;
import com.oficina.inventario.application.categoria.usecase.DesativarCategoriaUseCase;
import com.oficina.inventario.presentation.dto.CategoriaRequestDTO;
import com.oficina.inventario.presentation.dto.CategoriaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CriarCategoriaUseCase criarCategoriaUseCase;
    private final AtualizarCategoriaUseCase atualizarCategoriaUseCase;
    private final BuscarCategoriasUseCase buscarCategoriasUseCase;
    private final DesativarCategoriaUseCase desativarCategoriaUseCase;

    public CategoriaController(
            CriarCategoriaUseCase criarCategoriaUseCase,
            AtualizarCategoriaUseCase atualizarCategoriaUseCase,
            BuscarCategoriasUseCase buscarCategoriasUseCase,
            DesativarCategoriaUseCase desativarCategoriaUseCase
    ) {
        this.criarCategoriaUseCase = criarCategoriaUseCase;
        this.atualizarCategoriaUseCase = atualizarCategoriaUseCase;
        this.buscarCategoriasUseCase = buscarCategoriasUseCase;
        this.desativarCategoriaUseCase = desativarCategoriaUseCase;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodas(
            @RequestParam(required = false, defaultValue = "false") boolean apenasAtivas) {

        List<CategoriaOutput> categorias = buscarCategoriasUseCase.executar(null);

        List<CategoriaResponseDTO> response = categorias.stream()
                .filter(cat -> !apenasAtivas || cat.ativo())
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criar(@Valid @RequestBody CategoriaRequestDTO requestDTO) {
        CriarCategoriaInput input = new CriarCategoriaInput(
                requestDTO.getNome(),
                requestDTO.getDescricao()
        );

        CategoriaOutput output = criarCategoriaUseCase.executar(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDTO(output));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(
            @PathVariable String id,
            @Valid @RequestBody CategoriaRequestDTO requestDTO) {

        AtualizarCategoriaInput input = new AtualizarCategoriaInput(
                id,
                requestDTO.getNome(),
                requestDTO.getDescricao()
        );

        CategoriaOutput output = atualizarCategoriaUseCase.executar(input);
        return ResponseEntity.ok(toResponseDTO(output));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable String id) {
        desativarCategoriaUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }

    private CategoriaResponseDTO toResponseDTO(CategoriaOutput output) {
        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setId(output.id());
        dto.setNome(output.nome());
        dto.setDescricao(output.descricao());
        dto.setAtivo(output.ativo());
        dto.setCreatedAt(output.createdAt());
        dto.setUpdatedAt(output.updatedAt());
        return dto;
    }
}
