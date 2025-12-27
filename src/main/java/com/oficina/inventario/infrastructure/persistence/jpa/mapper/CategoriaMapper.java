package com.oficina.inventario.infrastructure.persistence.jpa.mapper;

import com.oficina.inventario.domain.categoria.Categoria;
import com.oficina.inventario.domain.categoria.CategoriaId;
import com.oficina.inventario.domain.categoria.NomeCategoria;
import com.oficina.inventario.infrastructure.persistence.jpa.entity.CategoriaJpaEntity;

public class CategoriaMapper {

    public static CategoriaJpaEntity toJpaEntity(Categoria categoria) {
        CategoriaJpaEntity jpaEntity = new CategoriaJpaEntity();
        jpaEntity.setId(categoria.getId().getValor());
        jpaEntity.setNome(categoria.getNome().getValor());
        jpaEntity.setDescricao(categoria.getDescricao());
        jpaEntity.setAtivo(categoria.isAtivo());
        jpaEntity.setCreatedAt(categoria.getCreatedAt());
        jpaEntity.setUpdatedAt(categoria.getUpdatedAt());
        return jpaEntity;
    }

    public static Categoria toDomain(CategoriaJpaEntity jpaEntity) {
        Categoria categoria = Categoria.criar(
                NomeCategoria.of(jpaEntity.getNome()),
                jpaEntity.getDescricao()
        );
        categoria.setId(CategoriaId.of(jpaEntity.getId()));
        return categoria;
    }
}
