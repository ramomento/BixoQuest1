package com.bixoquest.model;

import java.util.List;

public class ConsultarProgresso extends Acao {

    @Override
    public void executarAcao(Aluno aluno, Jogo jogo) {
        // Apenas marca que foi consultado
        // A UI é responsável por exibir via pop-up
    }

    @Override
    public boolean podeExecutar(Aluno aluno) {
        // Sempre pode consultar progresso
        return true;
    }
}