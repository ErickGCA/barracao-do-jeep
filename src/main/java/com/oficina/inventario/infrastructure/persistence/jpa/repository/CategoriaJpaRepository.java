package com.oficina.inventario.infrastructure.persistence.jpa.repository;

import com.oficina.inventario.infrastructure.persistence.jpa.entity.CategoriaJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaJpaRepository extends JpaRepository<CategoriaJpaEntity, String> {

    List<CategoriaJpaEntity> findByAtivoTrue();

    boolean existsByNome(String nome);
}
