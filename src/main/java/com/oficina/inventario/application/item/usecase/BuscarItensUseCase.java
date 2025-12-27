package com.oficina.inventario.application.item.usecase;

import com.oficina.inventario.application.item.dto.ItemOutput;
import com.oficina.inventario.application.shared.UseCase;
import com.oficina.inventario.domain.item.ItemRepository;

import java.util.List;
import java.util.stream.Collectors;

public class BuscarItensUseCase implements UseCase<Void, List<ItemOutput>> {

    private final ItemRepository itemRepository;

    public BuscarItensUseCase(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<ItemOutput> executar(Void input) {
        return itemRepository.buscarTodos()
                .stream()
                .map(ItemOutput::from)
                .collect(Collectors.toList());
    }
}
