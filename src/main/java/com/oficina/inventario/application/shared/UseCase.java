package com.oficina.inventario.application.shared;

/**
 * Interface base para Use Cases (Clean Architecture)
 * Um Use Case representa uma acao especifica que o sistema pode realizar
 *
 * @param <INPUT> DTO de entrada
 * @param <OUTPUT> DTO de saida
 */
public interface UseCase<INPUT, OUTPUT> {

    /**
     * Executa o caso de uso
     */
    OUTPUT executar(INPUT input);
}
