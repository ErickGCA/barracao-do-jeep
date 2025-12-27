package com.oficina.inventario.infrastructure.persistence.adapter;

import com.oficina.inventario.domain.item.ItemId;
import com.oficina.inventario.domain.movimentacao.Movimentacao;
import com.oficina.inventario.domain.movimentacao.MovimentacaoId;
import com.oficina.inventario.domain.movimentacao.MovimentacaoRepository;
import com.oficina.inventario.domain.movimentacao.TipoMovimentacao;
import com.oficina.inventario.infrastructure.persistence.jpa.entity.MovimentacaoJpaEntity;
import com.oficina.inventario.infrastructure.persistence.jpa.mapper.MovimentacaoMapper;
import com.oficina.inventario.infrastructure.persistence.jpa.repository.MovimentacaoJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MovimentacaoRepositoryAdapter implements MovimentacaoRepository {

    private final MovimentacaoJpaRepository jpaRepository;

    public MovimentacaoRepositoryAdapter(MovimentacaoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Movimentacao salvar(Movimentacao movimentacao) {
        if (movimentacao.getId() == null) {
            movimentacao.setId(MovimentacaoId.gerar());
        }
        MovimentacaoJpaEntity jpaEntity = MovimentacaoMapper.toJpaEntity(movimentacao);
        MovimentacaoJpaEntity saved = jpaRepository.save(jpaEntity);
        return MovimentacaoMapper.toDomain(saved);
    }

    @Override
    public Optional<Movimentacao> buscarPorId(MovimentacaoId id) {
        return jpaRepository.findById(id.getValor())
                .map(MovimentacaoMapper::toDomain);
    }

    @Override
    public List<Movimentacao> buscarTodas() {
        return jpaRepository.findAll()
                .stream()
                .map(MovimentacaoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movimentacao> buscarPorItem(ItemId itemId) {
        return jpaRepository.findByItemId(itemId.getValor())
                .stream()
                .map(MovimentacaoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movimentacao> buscarPorTipo(TipoMovimentacao tipo) {
        return jpaRepository.findByTipo(tipo.name())
                .stream()
                .map(MovimentacaoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movimentacao> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return jpaRepository.findByDataMovimentacaoBetween(dataInicio, dataFim)
                .stream()
                .map(MovimentacaoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movimentacao> buscarPorItemEPeriodo(ItemId itemId, LocalDateTime dataInicio, LocalDateTime dataFim) {
        return jpaRepository.findByItemIdAndDataMovimentacaoBetween(
                        itemId.getValor(),
                        dataInicio,
                        dataFim
                )
                .stream()
                .map(MovimentacaoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void excluir(MovimentacaoId id) {
        jpaRepository.deleteById(id.getValor());
    }
}
