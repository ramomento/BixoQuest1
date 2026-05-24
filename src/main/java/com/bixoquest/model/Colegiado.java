package com.bixoquest.model;
import java.util.ArrayList;
import java.util.List;

public class Colegiado extends Local {
    public Colegiado(Jogo jogo) {
        super("Colegiado", jogo);
    }

    @Override
    public List<Acao> getAcoesDisponiveis() {
        List<Acao> acoes = new ArrayList<>();
        acoes.add(new PedirConselho());
        acoes.add(new ConsultarProgresso());
        return acoes;
    }
}