// PontoDeOnibus.java
package com.bixoquest.model;
import java.util.ArrayList;
import java.util.List;

public class PontoDeOnibus extends Local {
    public PontoDeOnibus(Jogo jogo) {
        super("Ponto de Ônibus", jogo);
    }

    @Override
    public List<Acao> getAcoesDisponiveis() {
        List<Acao> acoes = new ArrayList<>();
        acoes.add(new FinalizarTurno());
        return acoes;
    }
}