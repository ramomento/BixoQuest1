package com.bixoquest.model;
import java.util.List;
import java.util.ArrayList;

public class Cantina extends Local {
    public Cantina(Jogo jogo) {
        super("Cantina", jogo);
    }

    @Override
    public List<Acao> getAcoesDisponiveis() {
        List<Acao> acoes = new ArrayList<>();

        // Adiciona Comer para cada item
        acoes.add(new Comer(new ItemCantina("Café", 10.0, 999)));
        acoes.add(new Comer(new ItemCantina("Sanduíche", 15.0, 999)));
        acoes.add(new Comer(new ItemCantina("Torta", 20.0, 999)));
        acoes.add(new Comer(new ItemCantina("Remédio", 25.0, 999)));

        // Interagir com animais
        acoes.add(new InteragirAnimais());

        return acoes;
    }
}