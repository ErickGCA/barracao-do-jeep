package com.oficina.inventario.service;

import com.oficina.inventario.dto.MovimentacaoRequestDTO;
import com.oficina.inventario.dto.MovimentacaoResponseDTO;
import com.oficina.inventario.entity.Item;
import com.oficina.inventario.entity.Movimentacao;
import com.oficina.inventario.enums.TipoMovimentacao;
import com.oficina.inventario.exception.BusinessException;
import com.oficina.inventario.exception.ResourceNotFoundException;
import com.oficina.inventario.repository.ItemRepository;
import com.oficina.inventario.repository.MovimentacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public List<MovimentacaoResponseDTO> listarTodas() {
        return movimentacaoRepository.findAll().stream()
                .map(MovimentacaoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MovimentacaoResponseDTO buscarPorId(Long id) {
        Movimentacao movimentacao = movimentacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimentação não encontrada com ID: " + id));
        return MovimentacaoResponseDTO.fromEntity(movimentacao);
    }

    @Transactional(readOnly = true)
    public List<MovimentacaoResponseDTO> listarPorItem(Long itemId) {
        return movimentacaoRepository.findByItemIdOrderByDataMovimentacaoDesc(itemId).stream()
                .map(MovimentacaoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public MovimentacaoResponseDTO registrarEntrada(MovimentacaoRequestDTO requestDTO) {
        Item item = buscarItemAtivo(requestDTO.getItemId());

        int quantidadeAnterior = item.getQuantidadeAtual();
        int quantidadeNova = quantidadeAnterior + requestDTO.getQuantidade();

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setItem(item);
        movimentacao.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
        movimentacao.setQuantidade(requestDTO.getQuantidade());
        movimentacao.setQuantidadeAnterior(quantidadeAnterior);
        movimentacao.setQuantidadeNova(quantidadeNova);
        movimentacao.setObservacao(requestDTO.getObservacao());
        movimentacao.setResponsavel(requestDTO.getResponsavel());

        item.setQuantidadeAtual(quantidadeNova);
        itemRepository.save(item);

        Movimentacao movimentacaoSalva = movimentacaoRepository.save(movimentacao);
        return MovimentacaoResponseDTO.fromEntity(movimentacaoSalva);
    }

    @Transactional
    public MovimentacaoResponseDTO registrarSaida(MovimentacaoRequestDTO requestDTO) {
        Item item = buscarItemAtivo(requestDTO.getItemId());

        int quantidadeAnterior = item.getQuantidadeAtual();

        if (requestDTO.getQuantidade() > quantidadeAnterior) {
            throw new BusinessException(
                    String.format("Quantidade solicitada (%d) é maior que a disponível em estoque (%d)",
                            requestDTO.getQuantidade(), quantidadeAnterior)
            );
        }

        int quantidadeNova = quantidadeAnterior - requestDTO.getQuantidade();

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setItem(item);
        movimentacao.setTipoMovimentacao(TipoMovimentacao.SAIDA);
        movimentacao.setQuantidade(requestDTO.getQuantidade());
        movimentacao.setQuantidadeAnterior(quantidadeAnterior);
        movimentacao.setQuantidadeNova(quantidadeNova);
        movimentacao.setObservacao(requestDTO.getObservacao());
        movimentacao.setResponsavel(requestDTO.getResponsavel());

        item.setQuantidadeAtual(quantidadeNova);
        itemRepository.save(item);

        Movimentacao movimentacaoSalva = movimentacaoRepository.save(movimentacao);
        return MovimentacaoResponseDTO.fromEntity(movimentacaoSalva);
    }

    private Item buscarItemAtivo(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com ID: " + itemId));

        if (!item.getAtivo()) {
            throw new BusinessException("Não é possível movimentar um item inativo");
        }

        return item;
    }
}
