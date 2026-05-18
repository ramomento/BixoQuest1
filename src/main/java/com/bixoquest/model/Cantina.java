package com.bixoquest.model;

import java.util.ArrayList;
import java.util.List;

public class Cantina extends Local {
    private List<ItemCantina> itens;

    public Cantina(Jogo jogo) {
        super("Cantina", jogo);
        this.itens = new ArrayList<>();
        itens.add(new ItemCantina("Remédio",   10.0, 3));
        itens.add(new ItemCantina("Sanduíche",  8.0, 5));
        itens.add(new ItemCantina("Café",        4.0, 5));
        itens.add(new ItemCantina("Torta",      15.0, 3));
    }

    @Override
    public List<Acao> getAcoesDisponiveis() {
        List<Acao> acoes = new ArrayList<>();
        acoes.add(new InteragirAnimais());
        for (ItemCantina item : itens) {
            if (item.estaDisponivel()) acoes.add(new Comer(item));
        }
        return acoes;
    }

    public List<ItemCantina> getItens() { return itens; }
}