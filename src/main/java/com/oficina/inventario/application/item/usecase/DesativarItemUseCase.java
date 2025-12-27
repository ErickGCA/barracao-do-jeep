package com.oficina.inventario.application.item.usecase;

import com.oficina.inventario.application.shared.UseCase;
import com.oficina.inventario.domain.item.Item;
import com.oficina.inventario.domain.item.ItemId;
import com.oficina.inventario.domain.item.ItemRepository;

public class DesativarItemUseCase implements UseCase<String, Void> {

    private final ItemRepository itemRepository;

    public DesativarItemUseCase(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Void executar(String itemIdStr) {
        ItemId itemId = ItemId.of(itemIdStr);
        Item item = itemRepository.buscarPorId(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado"));

        item.desativar();
        itemRepository.salvar(item);

        return null;
    }
}
