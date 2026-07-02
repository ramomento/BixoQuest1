package com.bixoquest.controller;

import com.bixoquest.model.*;

/**
 * Interface Mediator.
 * Define o contrato para mediação entre componentes do jogo.
 */
public interface Mediator {

    /**
     * Executa uma ação após validação.
     */
    void executarAcao(Acao acao);

    /**
     * Move o aluno para um novo local.
     */
    void moverPara(Local local);

    /**
     * Finaliza o turno atual.
     */
    void finalizarTurno();

    /**
     * Notifica que o aluno executou uma ação.
     */
    void notificarAcao(Acao acao);

    /**
     * Notifica que o aluno se moveu para um local.
     */
    void notificarMovimento(Local local);

    /**
     * Notifica que o turno foi finalizado.
     */
    void notificarFinalizacaoTurno();

    /**
     * Coordena a execução de uma ação validando pré-condições.
     */
    boolean coordenarAcao(Acao acao, Aluno aluno);

    /**
     * Verifica se o aluno pode realizar uma determinada ação.
     */
    boolean podeExecutar(Acao acao);
}