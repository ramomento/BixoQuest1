package com.bixoquest.model;

public abstract class Evento {
    private int turnoAplicacao;
    private boolean ativo;

    public abstract void aplicar(Aluno aluno, Jogo jogo);

    // só executa se estiver ativo e no turno correto
    public boolean deveExecutar(int turnoAtual){
        return ativo && turnoAtual == turnoAplicacao;
    }

    public int getTurnoAplicacao() { return turnoAplicacao; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public void setTurnoAplicacao(int turnoAplicacao) { this.turnoAplicacao = turnoAplicacao; }
}