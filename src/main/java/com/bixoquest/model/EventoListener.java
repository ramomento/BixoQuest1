package com.bixoquest.model;

/**
 * Observer que escuta eventos do jogo.
 * Implementa o padrão Observer para desacoplar produtores de eventos dos consumidores.
 */
public interface EventoListener {

    /**
     * Chamado quando um evento é aplicado.
     */
    void aoEventoAplicado(Evento evento, Aluno aluno);

    /**
     * Chamado quando um evento é descartado/não executado.
     */
    void aoEventoDescartado(Evento evento);
}