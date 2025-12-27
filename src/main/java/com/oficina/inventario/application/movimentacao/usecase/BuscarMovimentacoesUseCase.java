package com.oficina.inventario.application.movimentacao.usecase;

import com.oficina.inventario.application.movimentacao.dto.MovimentacaoOutput;
import com.oficina.inventario.application.shared.UseCase;
import com.oficina.inventario.domain.movimentacao.MovimentacaoRepository;

import java.util.List;
import java.util.stream.Collectors;

public class BuscarMovimentacoesUseCase implements UseCase<Void, List<MovimentacaoOutput>> {

    private final MovimentacaoRepository movimentacaoRepository;

    public BuscarMovimentacoesUseCase(MovimentacaoRepository movimentacaoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
    }

    @Override
    public List<MovimentacaoOutput> executar(Void input) {
        return movimentacaoRepository.buscarTodas()
                .stream()
                .map(MovimentacaoOutput::from)
                .collect(Collectors.toList());
    }
}
