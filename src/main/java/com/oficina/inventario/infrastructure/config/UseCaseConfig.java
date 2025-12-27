package com.oficina.inventario.infrastructure.config;

import com.oficina.inventario.application.categoria.usecase.AtualizarCategoriaUseCase;
import com.oficina.inventario.application.categoria.usecase.BuscarCategoriasUseCase;
import com.oficina.inventario.application.categoria.usecase.CriarCategoriaUseCase;
import com.oficina.inventario.application.categoria.usecase.DesativarCategoriaUseCase;
import com.oficina.inventario.application.item.usecase.AtualizarItemUseCase;
import com.oficina.inventario.application.item.usecase.BuscarItensUseCase;
import com.oficina.inventario.application.item.usecase.CriarItemUseCase;
import com.oficina.inventario.application.item.usecase.RegistrarEntradaUseCase;
import com.oficina.inventario.application.item.usecase.RegistrarSaidaUseCase;
import com.oficina.inventario.application.movimentacao.usecase.BuscarMovimentacoesUseCase;
import com.oficina.inventario.domain.categoria.CategoriaRepository;
import com.oficina.inventario.domain.item.ItemRepository;
import com.oficina.inventario.domain.movimentacao.MovimentacaoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CriarCategoriaUseCase criarCategoriaUseCase(CategoriaRepository categoriaRepository) {
        return new CriarCategoriaUseCase(categoriaRepository);
    }

    @Bean
    public AtualizarCategoriaUseCase atualizarCategoriaUseCase(CategoriaRepository categoriaRepository) {
        return new AtualizarCategoriaUseCase(categoriaRepository);
    }

    @Bean
    public BuscarCategoriasUseCase buscarCategoriasUseCase(CategoriaRepository categoriaRepository) {
        return new BuscarCategoriasUseCase(categoriaRepository);
    }

    @Bean
    public DesativarCategoriaUseCase desativarCategoriaUseCase(CategoriaRepository categoriaRepository) {
        return new DesativarCategoriaUseCase(categoriaRepository);
    }

    @Bean
    public CriarItemUseCase criarItemUseCase(ItemRepository itemRepository) {
        return new CriarItemUseCase(itemRepository);
    }

    @Bean
    public AtualizarItemUseCase atualizarItemUseCase(ItemRepository itemRepository) {
        return new AtualizarItemUseCase(itemRepository);
    }

    @Bean
    public BuscarItensUseCase buscarItensUseCase(ItemRepository itemRepository) {
        return new BuscarItensUseCase(itemRepository);
    }

    @Bean
    public RegistrarEntradaUseCase registrarEntradaUseCase(
            ItemRepository itemRepository,
            MovimentacaoRepository movimentacaoRepository
    ) {
        return new RegistrarEntradaUseCase(itemRepository, movimentacaoRepository);
    }

    @Bean
    public RegistrarSaidaUseCase registrarSaidaUseCase(
            ItemRepository itemRepository,
            MovimentacaoRepository movimentacaoRepository
    ) {
        return new RegistrarSaidaUseCase(itemRepository, movimentacaoRepository);
    }

    @Bean
    public BuscarMovimentacoesUseCase buscarMovimentacoesUseCase(MovimentacaoRepository movimentacaoRepository) {
        return new BuscarMovimentacoesUseCase(movimentacaoRepository);
    }
}
