package com.bixoquest.model;

public abstract class Acao {
    private int custoEnergia;
    private int custoTempo;

    // cada ação define seu próprio comportamento e condições de execução
    public abstract void executarAcao(Aluno aluno, Jogo jogo);
    public abstract boolean podeExecutar(Aluno aluno);

    public int getCustoEnergia(){ return custoEnergia; }
    public int getCustoTempo(){ return custoTempo; }
}