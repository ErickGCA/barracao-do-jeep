package com.oficina.inventario.repository;

import com.oficina.inventario.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByAtivoTrue();

    Optional<Categoria> findByNome(String nome);

    boolean existsByNome(String nome);
}
