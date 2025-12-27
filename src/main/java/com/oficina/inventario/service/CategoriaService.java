package com.oficina.inventario.service;

import com.oficina.inventario.dto.CategoriaRequestDTO;
import com.oficina.inventario.dto.CategoriaResponseDTO;
import com.oficina.inventario.entity.Categoria;
import com.oficina.inventario.exception.BusinessException;
import com.oficina.inventario.exception.ResourceNotFoundException;
import com.oficina.inventario.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(CategoriaResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarAtivas() {
        return categoriaRepository.findByAtivoTrue().stream()
                .map(CategoriaResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));
        return CategoriaResponseDTO.fromEntity(categoria);
    }

    @Transactional
    public CategoriaResponseDTO criar(CategoriaRequestDTO requestDTO) {
        if (categoriaRepository.existsByNome(requestDTO.getNome())) {
            throw new BusinessException("Já existe uma categoria com o nome: " + requestDTO.getNome());
        }

        Categoria categoria = new Categoria();
        categoria.setNome(requestDTO.getNome());
        categoria.setDescricao(requestDTO.getDescricao());
        categoria.setAtivo(true);

        Categoria categoriaSalva = categoriaRepository.save(categoria);
        return CategoriaResponseDTO.fromEntity(categoriaSalva);
    }

    @Transactional
    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO requestDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));

        if (!categoria.getNome().equals(requestDTO.getNome()) &&
                categoriaRepository.existsByNome(requestDTO.getNome())) {
            throw new BusinessException("Já existe uma categoria com o nome: " + requestDTO.getNome());
        }

        categoria.setNome(requestDTO.getNome());
        categoria.setDescricao(requestDTO.getDescricao());

        Categoria categoriaAtualizada = categoriaRepository.save(categoria);
        return CategoriaResponseDTO.fromEntity(categoriaAtualizada);
    }

    @Transactional
    public void desativar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));

        categoria.setAtivo(false);
        categoriaRepository.save(categoria);
    }

    @Transactional
    public void deletar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada com ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}
