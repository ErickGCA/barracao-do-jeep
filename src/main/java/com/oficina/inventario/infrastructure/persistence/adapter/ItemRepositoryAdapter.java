package com.oficina.inventario.infrastructure.persistence.adapter;

import com.oficina.inventario.domain.categoria.CategoriaId;
import com.oficina.inventario.domain.item.CodigoItem;
import com.oficina.inventario.domain.item.Item;
import com.oficina.inventario.domain.item.ItemId;
import com.oficina.inventario.domain.item.ItemRepository;
import com.oficina.inventario.infrastructure.persistence.jpa.entity.ItemJpaEntity;
import com.oficina.inventario.infrastructure.persistence.jpa.mapper.ItemMapper;
import com.oficina.inventario.infrastructure.persistence.jpa.repository.ItemJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ItemRepositoryAdapter implements ItemRepository {

    private final ItemJpaRepository jpaRepository;

    public ItemRepositoryAdapter(ItemJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Item salvar(Item item) {
        if (item.getId() == null) {
            item.setId(ItemId.gerar());
        }
        ItemJpaEntity jpaEntity = ItemMapper.toJpaEntity(item);
        ItemJpaEntity saved = jpaRepository.save(jpaEntity);
        return ItemMapper.toDomain(saved);
    }

    @Override
    public Optional<Item> buscarPorId(ItemId id) {
        return jpaRepository.findById(id.getValor())
                .map(ItemMapper::toDomain);
    }

    @Override
    public Optional<Item> buscarPorCodigo(CodigoItem codigo) {
        return jpaRepository.findByCodigo(codigo.getValor())
                .map(ItemMapper::toDomain);
    }

    @Override
    public List<Item> buscarTodos() {
        return jpaRepository.findAll()
                .stream()
                .map(ItemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> buscarPorCategoria(CategoriaId categoriaId) {
        return jpaRepository.findByCategoriaId(categoriaId.getValor())
                .stream()
                .map(ItemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> buscarAtivos() {
        return jpaRepository.findByAtivoTrue()
                .stream()
                .map(ItemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> buscarComEstoqueBaixo() {
        return jpaRepository.findComEstoqueBaixo()
                .stream()
                .map(ItemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorCodigo(CodigoItem codigo) {
        return jpaRepository.existsByCodigo(codigo.getValor());
    }

    @Override
    public void excluir(ItemId id) {
        jpaRepository.deleteById(id.getValor());
    }
}
