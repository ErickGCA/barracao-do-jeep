package com.oficina.inventario.infrastructure.persistence.adapter;

import com.oficina.inventario.domain.categoria.Categoria;
import com.oficina.inventario.domain.categoria.CategoriaId;
import com.oficina.inventario.domain.categoria.CategoriaRepository;
import com.oficina.inventario.domain.categoria.NomeCategoria;
import com.oficina.inventario.infrastructure.persistence.jpa.entity.CategoriaJpaEntity;
import com.oficina.inventario.infrastructure.persistence.jpa.mapper.CategoriaMapper;
import com.oficina.inventario.infrastructure.persistence.jpa.repository.CategoriaJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CategoriaRepositoryAdapter implements CategoriaRepository {

    private final CategoriaJpaRepository jpaRepository;

    public CategoriaRepositoryAdapter(CategoriaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Categoria salvar(Categoria categoria) {
        if (categoria.getId() == null) {
            categoria.setId(CategoriaId.gerar());
        }
        CategoriaJpaEntity jpaEntity = CategoriaMapper.toJpaEntity(categoria);
        CategoriaJpaEntity saved = jpaRepository.save(jpaEntity);
        return CategoriaMapper.toDomain(saved);
    }

    @Override
    public Optional<Categoria> buscarPorId(CategoriaId id) {
        return jpaRepository.findById(id.getValor())
                .map(CategoriaMapper::toDomain);
    }

    @Override
    public List<Categoria> buscarTodas() {
        return jpaRepository.findAll()
                .stream()
                .map(CategoriaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Categoria> buscarAtivas() {
        return jpaRepository.findByAtivoTrue()
                .stream()
                .map(CategoriaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorNome(NomeCategoria nome) {
        return jpaRepository.existsByNome(nome.getValor());
    }

    @Override
    public void excluir(CategoriaId id) {
        jpaRepository.deleteById(id.getValor());
    }
}
