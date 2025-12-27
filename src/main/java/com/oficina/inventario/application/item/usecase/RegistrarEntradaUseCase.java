package com.oficina.inventario.application.item.usecase;

import com.oficina.inventario.application.item.dto.ItemOutput;
import com.oficina.inventario.application.item.dto.RegistrarMovimentacaoInput;
import com.oficina.inventario.application.shared.UseCase;
import com.oficina.inventario.domain.item.Item;
import com.oficina.inventario.domain.item.ItemId;
import com.oficina.inventario.domain.item.ItemRepository;
import com.oficina.inventario.domain.item.Quantidade;
import com.oficina.inventario.domain.movimentacao.Movimentacao;
import com.oficina.inventario.domain.movimentacao.MovimentacaoRepository;
import com.oficina.inventario.domain.movimentacao.Responsavel;

public class RegistrarEntradaUseCase implements UseCase<RegistrarMovimentacaoInput, ItemOutput> {

    private final ItemRepository itemRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public RegistrarEntradaUseCase(ItemRepository itemRepository, MovimentacaoRepository movimentacaoRepository) {
        this.itemRepository = itemRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    @Override
    public ItemOutput executar(RegistrarMovimentacaoInput input) {
        ItemId itemId = ItemId.of(input.itemId());
        Quantidade quantidade = Quantidade.of(input.quantidade());
        Responsavel responsavel = Responsavel.of(input.responsavel());

        Item item = itemRepository.buscarPorId(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item nao encontrado"));

        item.registrarEntrada(quantidade);
        Item itemAtualizado = itemRepository.salvar(item);

        Movimentacao movimentacao = Movimentacao.registrarEntrada(
                itemId,
                quantidade,
                responsavel,
                input.observacao()
        );
        movimentacaoRepository.salvar(movimentacao);

        return ItemOutput.from(itemAtualizado);
    }
}
