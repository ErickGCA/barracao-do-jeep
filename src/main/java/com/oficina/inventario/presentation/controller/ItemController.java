package com.oficina.inventario.presentation.controller;

import com.oficina.inventario.application.item.dto.AtualizarItemInput;
import com.oficina.inventario.application.item.dto.CriarItemInput;
import com.oficina.inventario.application.item.dto.ItemOutput;
import com.oficina.inventario.application.item.dto.RegistrarMovimentacaoInput;
import com.oficina.inventario.application.item.usecase.AtualizarItemUseCase;
import com.oficina.inventario.application.item.usecase.BuscarItensUseCase;
import com.oficina.inventario.application.item.usecase.CriarItemUseCase;
import com.oficina.inventario.application.item.usecase.DesativarItemUseCase;
import com.oficina.inventario.application.item.usecase.RegistrarEntradaUseCase;
import com.oficina.inventario.application.item.usecase.RegistrarSaidaUseCase;
import com.oficina.inventario.presentation.dto.ItemRequestDTO;
import com.oficina.inventario.presentation.dto.ItemResponseDTO;
import com.oficina.inventario.presentation.dto.MovimentacaoRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/itens")
public class ItemController {

    private final CriarItemUseCase criarItemUseCase;
    private final AtualizarItemUseCase atualizarItemUseCase;
    private final BuscarItensUseCase buscarItensUseCase;
    private final DesativarItemUseCase desativarItemUseCase;
    private final RegistrarEntradaUseCase registrarEntradaUseCase;
    private final RegistrarSaidaUseCase registrarSaidaUseCase;

    public ItemController(
            CriarItemUseCase criarItemUseCase,
            AtualizarItemUseCase atualizarItemUseCase,
            BuscarItensUseCase buscarItensUseCase,
            DesativarItemUseCase desativarItemUseCase,
            RegistrarEntradaUseCase registrarEntradaUseCase,
            RegistrarSaidaUseCase registrarSaidaUseCase
    ) {
        this.criarItemUseCase = criarItemUseCase;
        this.atualizarItemUseCase = atualizarItemUseCase;
        this.buscarItensUseCase = buscarItensUseCase;
        this.desativarItemUseCase = desativarItemUseCase;
        this.registrarEntradaUseCase = registrarEntradaUseCase;
        this.registrarSaidaUseCase = registrarSaidaUseCase;
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> listarTodos(
            @RequestParam(required = false, defaultValue = "false") boolean apenasAtivos) {

        List<ItemOutput> itens = buscarItensUseCase.executar(null);

        List<ItemResponseDTO> response = itens.stream()
                .filter(item -> !apenasAtivos || item.ativo())
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/estoque-baixo")
    public ResponseEntity<List<ItemResponseDTO>> listarEstoqueBaixo() {
        List<ItemOutput> itens = buscarItensUseCase.executar(null);

        List<ItemResponseDTO> response = itens.stream()
                .filter(item -> item.ativo() && item.quantidadeAtual() <= item.quantidadeMinima())
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ItemResponseDTO> criar(@Valid @RequestBody ItemRequestDTO requestDTO) {
        CriarItemInput input = new CriarItemInput(
                requestDTO.getCodigo(),
                requestDTO.getNome(),
                requestDTO.getDescricao(),
                requestDTO.getUnidadeMedida(),
                requestDTO.getQuantidadeMinima(),
                requestDTO.getLocalizacao(),
                requestDTO.getCategoriaId()
        );

        ItemOutput output = criarItemUseCase.executar(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDTO(output));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> atualizar(
            @PathVariable String id,
            @Valid @RequestBody ItemRequestDTO requestDTO) {

        AtualizarItemInput input = new AtualizarItemInput(
                id,
                requestDTO.getCodigo(),
                requestDTO.getNome(),
                requestDTO.getDescricao(),
                requestDTO.getUnidadeMedida(),
                requestDTO.getQuantidadeMinima(),
                requestDTO.getLocalizacao(),
                requestDTO.getCategoriaId()
        );

        ItemOutput output = atualizarItemUseCase.executar(input);
        return ResponseEntity.ok(toResponseDTO(output));
    }

    @PostMapping("/{id}/entrada")
    public ResponseEntity<ItemResponseDTO> registrarEntrada(
            @PathVariable String id,
            @Valid @RequestBody MovimentacaoRequestDTO requestDTO) {

        RegistrarMovimentacaoInput input = new RegistrarMovimentacaoInput(
                id,
                requestDTO.getQuantidade(),
                requestDTO.getResponsavel(),
                requestDTO.getObservacao()
        );

        ItemOutput output = registrarEntradaUseCase.executar(input);
        return ResponseEntity.ok(toResponseDTO(output));
    }

    @PostMapping("/{id}/saida")
    public ResponseEntity<ItemResponseDTO> registrarSaida(
            @PathVariable String id,
            @Valid @RequestBody MovimentacaoRequestDTO requestDTO) {

        RegistrarMovimentacaoInput input = new RegistrarMovimentacaoInput(
                id,
                requestDTO.getQuantidade(),
                requestDTO.getResponsavel(),
                requestDTO.getObservacao()
        );

        ItemOutput output = registrarSaidaUseCase.executar(input);
        return ResponseEntity.ok(toResponseDTO(output));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable String id) {
        desativarItemUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }

    private ItemResponseDTO toResponseDTO(ItemOutput output) {
        ItemResponseDTO dto = new ItemResponseDTO();
        dto.setId(output.id());
        dto.setCodigo(output.codigo());
        dto.setNome(output.nome());
        dto.setDescricao(output.descricao());
        dto.setUnidadeMedida(output.unidadeMedida());
        dto.setQuantidadeAtual(output.quantidadeAtual());
        dto.setQuantidadeMinima(output.quantidadeMinima());
        dto.setLocalizacao(output.localizacao());
        dto.setCategoriaId(output.categoriaId());
        dto.setAtivo(output.ativo());
        dto.setCreatedAt(output.createdAt());
        dto.setUpdatedAt(output.updatedAt());
        return dto;
    }
}
