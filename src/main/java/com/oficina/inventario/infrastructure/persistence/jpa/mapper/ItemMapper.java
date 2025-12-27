package com.oficina.inventario.infrastructure.persistence.jpa.mapper;

import com.oficina.inventario.domain.categoria.CategoriaId;
import com.oficina.inventario.domain.item.CodigoItem;
import com.oficina.inventario.domain.item.Item;
import com.oficina.inventario.domain.item.ItemId;
import com.oficina.inventario.domain.item.Quantidade;
import com.oficina.inventario.domain.item.UnidadeMedida;
import com.oficina.inventario.infrastructure.persistence.jpa.entity.ItemJpaEntity;

public class ItemMapper {

    public static ItemJpaEntity toJpaEntity(Item item) {
        ItemJpaEntity jpaEntity = new ItemJpaEntity();
        jpaEntity.setId(item.getId().valor());
        jpaEntity.setCodigo(item.getCodigo().valor());
        jpaEntity.setNome(item.getNome());
        jpaEntity.setDescricao(item.getDescricao());
        jpaEntity.setUnidadeMedida(item.getUnidadeMedida().name());
        jpaEntity.setQuantidadeAtual(item.getQuantidadeAtual().valor());
        jpaEntity.setQuantidadeMinima(item.getQuantidadeMinima().valor());
        jpaEntity.setLocalizacao(item.getLocalizacao());
        jpaEntity.setCategoriaId(item.getCategoriaId().valor());
        jpaEntity.setAtivo(item.isAtivo());
        jpaEntity.setCreatedAt(item.getCreatedAt());
        jpaEntity.setUpdatedAt(item.getUpdatedAt());
        return jpaEntity;
    }

    public static Item toDomain(ItemJpaEntity jpaEntity) {
        Item item = Item.criar(
                CodigoItem.of(jpaEntity.getCodigo()),
                jpaEntity.getNome(),
                jpaEntity.getDescricao(),
                UnidadeMedida.valueOf(jpaEntity.getUnidadeMedida()),
                Quantidade.of(jpaEntity.getQuantidadeMinima()),
                jpaEntity.getLocalizacao(),
                CategoriaId.of(jpaEntity.getCategoriaId())
        );
        item.setId(ItemId.of(jpaEntity.getId()));
        item.setQuantidadeAtual(Quantidade.of(jpaEntity.getQuantidadeAtual()));
        return item;
    }
}
