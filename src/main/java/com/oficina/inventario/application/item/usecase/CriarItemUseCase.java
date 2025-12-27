package com.oficina.inventario.application.item.usecase;

import com.oficina.inventario.application.item.dto.CriarItemInput;
import com.oficina.inventario.application.item.dto.ItemOutput;
import com.oficina.inventario.application.shared.UseCase;
import com.oficina.inventario.domain.categoria.CategoriaId;
import com.oficina.inventario.domain.item.CodigoItem;
import com.oficina.inventario.domain.item.Item;
import com.oficina.inventario.domain.item.ItemRepository;
import com.oficina.inventario.domain.item.Quantidade;
import com.oficina.inventario.domain.item.UnidadeMedida;

public class CriarItemUseCase implements UseCase<CriarItemInput, ItemOutput> {

    private final ItemRepository itemRepository;

    public CriarItemUseCase(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemOutput executar(CriarItemInput input) {
        CodigoItem codigo = CodigoItem.of(input.codigo());
        UnidadeMedida unidadeMedida = UnidadeMedida.valueOf(input.unidadeMedida());
        Quantidade quantidadeMinima = Quantidade.of(input.quantidadeMinima());
        CategoriaId categoriaId = CategoriaId.of(input.categoriaId());

        if (itemRepository.existePorCodigo(codigo)) {
            throw new IllegalArgumentException("Ja existe um item com este codigo");
        }

        Item item = Item.criar(
                codigo,
                input.nome(),
                input.descricao(),
                unidadeMedida,
                quantidadeMinima,
                input.localizacao(),
                categoriaId
        );

        Item itemSalvo = itemRepository.salvar(item);

        return ItemOutput.from(itemSalvo);
    }
}
