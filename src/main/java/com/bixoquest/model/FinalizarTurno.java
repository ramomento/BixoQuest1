package com.bixoquest.model;

public class FinalizarTurno extends Acao {

    @Override
    public void executarAcao(Aluno aluno, Jogo jogo) {
        // A lógica de finalização está em Jogo.finalizarTurno()
        jogo.finalizarTurno();
    }

    @Override
    public boolean podeExecutar(Aluno aluno) {
        // Sempre pode finalizar o turno no Ponto de Ônibus
        return true;
    }
}