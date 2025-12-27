package com.oficina.inventario.domain.shared;

import java.time.LocalDateTime;

/**
 * Interface para Eventos de Dominio (DDD)
 * Representa algo importante que aconteceu no dominio
 */
public interface DomainEvent {

    /**
     * Momento em que o evento ocorreu
     */
    LocalDateTime ocorridoEm();

    /**
     * Tipo do evento (usado para roteamento)
     */
    default String tipo() {
        return this.getClass().getSimpleName();
    }
}
