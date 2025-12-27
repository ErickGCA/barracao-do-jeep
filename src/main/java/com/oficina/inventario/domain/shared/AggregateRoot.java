package com.oficina.inventario.domain.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Classe base para Aggregate Roots (DDD)
 * Aggregates sao clusters de objetos de dominio tratados como uma unidade
 * O Aggregate Root e o ponto de entrada para o agregado
 */
public abstract class AggregateRoot<ID> {

    private final List<DomainEvent> eventos = new ArrayList<>();

    /**
     * Retorna o identificador unico do agregado
     */
    public abstract ID getId();

    /**
     * Registra um evento de dominio
     */
    protected void registrarEvento(DomainEvent evento) {
        this.eventos.add(evento);
    }

    /**
     * Retorna eventos de dominio registrados (imutavel)
     */
    public List<DomainEvent> getEventos() {
        return Collections.unmodifiableList(eventos);
    }

    /**
     * Limpa eventos apos serem publicados
     */
    public void limparEventos() {
        eventos.clear();
    }

    /**
     * Aggregates sao iguais se tem o mesmo ID
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AggregateRoot<?> that = (AggregateRoot<?>) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
