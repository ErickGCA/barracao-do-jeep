package com.oficina.inventario.domain.categoria;

import com.oficina.inventario.domain.shared.AggregateRoot;

import java.time.LocalDateTime;

public class Categoria extends AggregateRoot<CategoriaId> {

    private CategoriaId id;
    private NomeCategoria nome;
    private String descricao;
    private boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Categoria() {}

    public static Categoria criar(NomeCategoria nome, String descricao) {
        validarDescricao(descricao);

        Categoria categoria = new Categoria();
        categoria.nome = nome;
        categoria.descricao = descricao;
        categoria.ativo = true;
        categoria.createdAt = LocalDateTime.now();
        categoria.updatedAt = LocalDateTime.now();

        return categoria;
    }

    public void atualizar(NomeCategoria nome, String descricao) {
        validarDescricao(descricao);

        this.nome = nome;
        this.descricao = descricao;
        this.updatedAt = LocalDateTime.now();
    }

    public void desativar() {
        this.ativo = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void ativar() {
        this.ativo = true;
        this.updatedAt = LocalDateTime.now();
    }

    private static void validarDescricao(String descricao) {
        if (descricao != null && descricao.length() > 500) {
            throw new IllegalArgumentException("Descricao deve ter no maximo 500 caracteres");
        }
    }

    @Override
    public CategoriaId getId() {
        return id;
    }

    public void setId(CategoriaId id) {
        this.id = id;
    }

    public NomeCategoria getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
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
