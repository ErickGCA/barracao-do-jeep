package com.oficina.inventario.domain.movimentacao;

import com.oficina.inventario.domain.item.ItemId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MovimentacaoRepository {

    Movimentacao salvar(Movimentacao movimentacao);

    Optional<Movimentacao> buscarPorId(MovimentacaoId id);

    List<Movimentacao> buscarTodas();

    List<Movimentacao> buscarPorItem(ItemId itemId);

    List<Movimentacao> buscarPorTipo(TipoMovimentacao tipo);

    List<Movimentacao> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim);

    List<Movimentacao> buscarPorItemEPeriodo(ItemId itemId, LocalDateTime dataInicio, LocalDateTime dataFim);

    void excluir(MovimentacaoId id);
}
