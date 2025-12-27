package com.oficina.inventario.application.item.usecase;

import com.oficina.inventario.application.item.dto.AtualizarItemInput;
import com.oficina.inventario.application.item.dto.ItemOutput;
import com.oficina.inventario.application.shared.UseCase;
import com.oficina.inventario.domain.categoria.CategoriaId;
import com.oficina.inventario.domain.item.CodigoItem;
import com.oficina.inventario.domain.item.Item;
import com.oficina.inventario.domain.item.ItemId;
import com.oficina.inventario.domain.item.ItemRepository;
import com.oficina.inventario.domain.item.Quantidade;
import com.oficina.inventario.domain.item.UnidadeMedida;

public class AtualizarItemUseCase implements UseCase<AtualizarItemInput, ItemOutput> {

    private final ItemRepository itemRepository;

    public AtualizarItemUseCase(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemOutput executar(AtualizarItemInput input) {
        ItemId id = ItemId.of(input.id());
        CodigoItem codigo = CodigoItem.of(input.codigo());
        UnidadeMedida unidadeMedida = UnidadeMedida.valueOf(input.unidadeMedida());
        Quantidade quantidadeMinima = Quantidade.of(input.quantidadeMinima());
        CategoriaId categoriaId = CategoriaId.of(input.categoriaId());

        Item item = itemRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Item nao encontrado"));

        item.atualizar(
                codigo,
                input.nome(),
                input.descricao(),
                unidadeMedida,
                quantidadeMinima,
                input.localizacao(),
                categoriaId
        );

        Item itemAtualizado = itemRepository.salvar(item);

        return ItemOutput.from(itemAtualizado);
    }
}
