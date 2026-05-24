package com.bixoquest.model;
import java.util.List;

public abstract class Local {
    private String nome;
    private Jogo jogo;

    public Local(String nome, Jogo jogo) {
        this.nome = nome;
        this.jogo = jogo;
    }

    public abstract List<Acao> getAcoesDisponiveis();

    public String getNome() { return nome; }
    protected Jogo getJogo() { return jogo; }
}