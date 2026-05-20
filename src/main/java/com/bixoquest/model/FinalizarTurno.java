package com.bixoquest.model;

import com.bixoquest.enums.EstadoSaude;

public class FinalizarTurno extends Acao {
    private static final double CUSTO_TRANSPORTE = 10.0;

    @Override
    public void executarAcao(Aluno aluno, Jogo jogo) {
        if (aluno.temDinheiroSuficiente(CUSTO_TRANSPORTE)) {
            aluno.gastarDinheiro(CUSTO_TRANSPORTE);
        } else {
            aluno.alterarSaude(EstadoSaude.CANSADO);
            System.out.println("Você não tinha dinheiro para o ônibus e voltou a pé. Chegou em casa exausto.");
        }
        jogo.finalizarTurno();
    }

    @Override
    public boolean podeExecutar(Aluno aluno) {
        return true;
    }
}
