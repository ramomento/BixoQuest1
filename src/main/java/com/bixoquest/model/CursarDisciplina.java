package com.bixoquest.model;

public class CursarDisciplina extends Acao {

    @Override
    public void executarAcao(Aluno aluno, Jogo jogo) {
        // custo de 2 pontos de energia por aula
        aluno.reduzirEnergia(2);

        // conhecimento concedido depende do local atual
        if (aluno.getLocalAtual() instanceof SalaDeAula) {
            aluno.aumentarConhecimentoTeorico(25);
        } else if (aluno.getLocalAtual() instanceof Laboratorio) {
            aluno.aumentarConhecimentoPratico(25);
        }
    }

    @Override
    public boolean podeExecutar(Aluno aluno) {
        // requer pelo menos 2 de energia para cursar
        return aluno.getEnergia() >= 2;
    }
}