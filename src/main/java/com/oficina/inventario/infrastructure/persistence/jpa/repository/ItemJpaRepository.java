package com.oficina.inventario.infrastructure.persistence.jpa.repository;

import com.oficina.inventario.infrastructure.persistence.jpa.entity.ItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemJpaRepository extends JpaRepository<ItemJpaEntity, String> {

    Optional<ItemJpaEntity> findByCodigo(String codigo);

    List<ItemJpaEntity> findByCategoriaId(String categoriaId);

    List<ItemJpaEntity> findByAtivoTrue();

    @Query("SELECT i FROM ItemJpaEntity i WHERE i.quantidadeAtual <= i.quantidadeMinima")
    List<ItemJpaEntity> findComEstoqueBaixo();

    boolean existsByCodigo(String codigo);
}
