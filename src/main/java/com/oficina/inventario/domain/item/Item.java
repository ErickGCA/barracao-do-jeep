package com.oficina.inventario.domain.item;

import com.oficina.inventario.domain.categoria.CategoriaId;
import com.oficina.inventario.domain.movimentacao.Responsavel;
import com.oficina.inventario.domain.shared.AggregateRoot;

import java.time.LocalDateTime;

public class Item extends AggregateRoot<ItemId> {

    private ItemId id;
    private CodigoItem codigo;
    private String nome;
    private String descricao;
    private UnidadeMedida unidadeMedida;
    private Quantidade quantidadeAtual;
    private Quantidade quantidadeMinima;
    private String localizacao;
    private CategoriaId categoriaId;
    private boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Item() {}

    public static Item criar(
            CodigoItem codigo,
            String nome,
            String descricao,
            UnidadeMedida unidadeMedida,
            Quantidade quantidadeMinima,
            String localizacao,
            CategoriaId categoriaId
    ) {
        validarNome(nome);
        validarLocalizacao(localizacao);

        Item item = new Item();
        item.codigo = codigo;
        item.nome = nome;
        item.descricao = descricao;
        item.unidadeMedida = unidadeMedida;
        item.quantidadeAtual = Quantidade.zero();
        item.quantidadeMinima = quantidadeMinima;
        item.localizacao = localizacao;
        item.categoriaId = categoriaId;
        item.ativo = true;
        item.createdAt = LocalDateTime.now();
        item.updatedAt = LocalDateTime.now();

        return item;
    }

    public void atualizar(
            CodigoItem codigo,
            String nome,
            String descricao,
            UnidadeMedida unidadeMedida,
            Quantidade quantidadeMinima,
            String localizacao,
            CategoriaId categoriaId
    ) {
        validarNome(nome);
        validarLocalizacao(localizacao);

        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.unidadeMedida = unidadeMedida;
        this.quantidadeMinima = quantidadeMinima;
        this.localizacao = localizacao;
        this.categoriaId = categoriaId;
        this.updatedAt = LocalDateTime.now();
    }

    public void registrarEntrada(Quantidade quantidade) {
        validarAtivo();
        this.quantidadeAtual = this.quantidadeAtual.somar(quantidade);
        this.updatedAt = LocalDateTime.now();
    }

    public void registrarSaida(Quantidade quantidade) {
        validarAtivo();

        if (quantidade.maiorQue(this.quantidadeAtual)) {
            throw new EstoqueInsuficienteException(
                String.format("Quantidade solicitada (%d) maior que disponivel (%d)",
                    quantidade.getValor(), this.quantidadeAtual.getValor())
            );
        }

        this.quantidadeAtual = this.quantidadeAtual.subtrair(quantidade);
        this.updatedAt = LocalDateTime.now();

        if (this.quantidadeAtual.menorOuIgual(this.quantidadeMinima)) {
            registrarEvento(new ItemEstoqueBaixoEvent(this.id, this.quantidadeAtual, LocalDateTime.now()));
        }
    }

    public void desativar() {
        this.ativo = false;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean possuiEstoqueBaixo() {
        return this.quantidadeAtual.menorOuIgual(this.quantidadeMinima);
    }

    private void validarAtivo() {
        if (!this.ativo) {
            throw new ItemInativoException("Nao e possivel movimentar item inativo");
        }
    }

    private static void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome nao pode ser vazio");
        }
        if (nome.length() < 3 || nome.length() > 100) {
            throw new IllegalArgumentException("Nome deve ter entre 3 e 100 caracteres");
        }
    }

    private static void validarLocalizacao(String localizacao) {
        if (localizacao != null && localizacao.length() > 200) {
            throw new IllegalArgumentException("Localizacao deve ter no maximo 200 caracteres");
        }
    }

    @Override
    public ItemId getId() {
        return id;
    }

    public void setId(ItemId id) {
        this.id = id;
    }

    public void setQuantidadeAtual(Quantidade quantidadeAtual) {
        this.quantidadeAtual = quantidadeAtual;
    }

    public CodigoItem getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public Quantidade getQuantidadeAtual() {
        return quantidadeAtual;
    }

    public Quantidade getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public CategoriaId getCategoriaId() {
        return categoriaId;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
