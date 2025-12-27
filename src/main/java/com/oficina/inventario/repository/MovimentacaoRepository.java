package com.oficina.inventario.repository;

import com.oficina.inventario.entity.Movimentacao;
import com.oficina.inventario.enums.TipoMovimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    List<Movimentacao> findByItemId(Long itemId);

    List<Movimentacao> findByItemIdOrderByDataMovimentacaoDesc(Long itemId);

    List<Movimentacao> findByTipoMovimentacao(TipoMovimentacao tipo);

    List<Movimentacao> findByDataMovimentacaoBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Movimentacao> findByResponsavel(String responsavel);
}
