package com.bixoquest.model;

public class InteragirAnimais extends Acao {
    private static final double CHANCE_AUMENTO_MOTIVACAO = 0.5;

    @Override
    public void executarAcao(Aluno aluno, Jogo jogo) {
        if (Math.random() < CHANCE_AUMENTO_MOTIVACAO) {
            aluno.aumentarMotivacao();
        }
        aluno.incrementarInteracoesAnimais();
    }

    @Override
    public boolean podeExecutar(Aluno aluno) {
        return aluno.getInteracoesAnimais() == 0;
    }
}
