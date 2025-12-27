package com.oficina.inventario.infrastructure.persistence.jpa.repository;

import com.oficina.inventario.infrastructure.persistence.jpa.entity.MovimentacaoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimentacaoJpaRepository extends JpaRepository<MovimentacaoJpaEntity, String> {

    List<MovimentacaoJpaEntity> findByItemId(String itemId);

    List<MovimentacaoJpaEntity> findByTipo(String tipo);

    List<MovimentacaoJpaEntity> findByDataMovimentacaoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    List<MovimentacaoJpaEntity> findByItemIdAndDataMovimentacaoBetween(
            String itemId,
            LocalDateTime dataInicio,
            LocalDateTime dataFim
    );
}
