package com.oficina.inventario.repository;

import com.oficina.inventario.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByCodigo(String codigo);

    List<Item> findByAtivoTrue();

    List<Item> findByCategoriaId(Long categoriaId);

    List<Item> findByCategoriaIdAndAtivoTrue(Long categoriaId);

    @Query("SELECT i FROM Item i WHERE i.ativo = true AND i.quantidadeAtual <= i.quantidadeMinima")
    List<Item> findItensComEstoqueBaixo();

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdNot(String codigo, Long id);
}
