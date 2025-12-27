package com.oficina.inventario.service;

import com.oficina.inventario.dto.ItemRequestDTO;
import com.oficina.inventario.dto.ItemResponseDTO;
import com.oficina.inventario.entity.Categoria;
import com.oficina.inventario.entity.Item;
import com.oficina.inventario.exception.BusinessException;
import com.oficina.inventario.exception.ResourceNotFoundException;
import com.oficina.inventario.repository.CategoriaRepository;
import com.oficina.inventario.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<ItemResponseDTO> listarTodos() {
        return itemRepository.findAll().stream()
                .map(ItemResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ItemResponseDTO> listarAtivos() {
        return itemRepository.findByAtivoTrue().stream()
                .map(ItemResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ItemResponseDTO buscarPorId(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com ID: " + id));
        return ItemResponseDTO.fromEntity(item);
    }

    @Transactional(readOnly = true)
    public ItemResponseDTO buscarPorCodigo(String codigo) {
        Item item = itemRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com código: " + codigo));
        return ItemResponseDTO.fromEntity(item);
    }

    @Transactional(readOnly = true)
    public List<ItemResponseDTO> listarPorCategoria(Long categoriaId) {
        return itemRepository.findByCategoriaIdAndAtivoTrue(categoriaId).stream()
                .map(ItemResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ItemResponseDTO> listarItensComEstoqueBaixo() {
        return itemRepository.findItensComEstoqueBaixo().stream()
                .map(ItemResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemResponseDTO criar(ItemRequestDTO requestDTO) {
        if (itemRepository.existsByCodigo(requestDTO.getCodigo())) {
            throw new BusinessException("Já existe um item com o código: " + requestDTO.getCodigo());
        }

        Categoria categoria = categoriaRepository.findById(requestDTO.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + requestDTO.getCategoriaId()));

        if (!categoria.getAtivo()) {
            throw new BusinessException("Não é possível criar item para uma categoria inativa");
        }

        Item item = new Item();
        item.setCodigo(requestDTO.getCodigo());
        item.setNome(requestDTO.getNome());
        item.setDescricao(requestDTO.getDescricao());
        item.setUnidadeMedida(requestDTO.getUnidadeMedida());
        item.setQuantidadeAtual(0);
        item.setQuantidadeMinima(requestDTO.getQuantidadeMinima());
        item.setLocalizacao(requestDTO.getLocalizacao());
        item.setCategoria(categoria);
        item.setAtivo(true);

        Item itemSalvo = itemRepository.save(item);
        return ItemResponseDTO.fromEntity(itemSalvo);
    }

    @Transactional
    public ItemResponseDTO atualizar(Long id, ItemRequestDTO requestDTO) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com ID: " + id));

        if (!item.getCodigo().equals(requestDTO.getCodigo()) &&
                itemRepository.existsByCodigo(requestDTO.getCodigo())) {
            throw new BusinessException("Já existe um item com o código: " + requestDTO.getCodigo());
        }

        Categoria categoria = categoriaRepository.findById(requestDTO.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + requestDTO.getCategoriaId()));

        item.setCodigo(requestDTO.getCodigo());
        item.setNome(requestDTO.getNome());
        item.setDescricao(requestDTO.getDescricao());
        item.setUnidadeMedida(requestDTO.getUnidadeMedida());
        item.setQuantidadeMinima(requestDTO.getQuantidadeMinima());
        item.setLocalizacao(requestDTO.getLocalizacao());
        item.setCategoria(categoria);

        Item itemAtualizado = itemRepository.save(item);
        return ItemResponseDTO.fromEntity(itemAtualizado);
    }

    @Transactional
    public void desativar(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com ID: " + id));

        item.setAtivo(false);
        itemRepository.save(item);
    }

    @Transactional
    public void deletar(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item não encontrado com ID: " + id);
        }
        itemRepository.deleteById(id);
    }
}
