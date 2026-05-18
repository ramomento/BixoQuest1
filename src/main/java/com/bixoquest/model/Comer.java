package com.bixoquest.model;

import com.bixoquest.enums.EstadoSaude;

public class Comer extends Acao {
    private ItemCantina item;

    private static final String REMEDIO   = "Remédio";
    private static final String SANDUICHE = "Sanduíche";
    private static final String CAFE      = "Café";
    private static final String TORTA     = "Torta";

    public Comer(ItemCantina item) { this.item = item; }

    @Override
    public void executarAcao(Aluno aluno, Jogo jogo) {
        aluno.gastarDinheiro(item.getPreco());
        item.consumir();
        aplicarEfeito(aluno);
    }

    @Override
    public boolean podeExecutar(Aluno aluno) {
        return item.estaDisponivel() && aluno.temDinheiroSuficiente(item.getPreco());
    }

    private void aplicarEfeito(Aluno aluno) {
        switch (item.getNome()) {
            case REMEDIO   -> { if (aluno.getSaude() == EstadoSaude.DOENTE) aluno.alterarSaude(EstadoSaude.SAUDAVEL); }
            case SANDUICHE -> aluno.aumentarMotivacao();
            case CAFE      -> aluno.reduzirEnergia(-2);
            case TORTA     -> { aluno.reduzirEnergia(-2); aluno.aumentarMotivacao(); }
        }
    }

    public ItemCantina getItem() { return item; }
}