package com.bixoquest.model;

public class FinalizarTurno extends Acao {
    private static final double CUSTO_TRANSPORTE = 10.0;

    @Override
    public void executarAcao(Aluno aluno, Jogo jogo) {
        aluno.gastarDinheiro(CUSTO_TRANSPORTE);
        jogo.finalizarTurno();
    }

    @Override
    public boolean podeExecutar(Aluno aluno) {
        return aluno.temDinheiroSuficiente(CUSTO_TRANSPORTE);
    }
}
