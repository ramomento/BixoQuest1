package com.bixoquest.model;
import com.bixoquest.enums.TipoDisciplina;
import java.util.ArrayList;
import java.util.List;

public class Laboratorio extends Local {
    public Laboratorio(Jogo jogo) {
        super("Laboratório", jogo);
    }

    @Override
    public List<Acao> getAcoesDisponiveis() {
        List<Acao> acoes = new ArrayList<>();
        EventoProva prova = getJogo().getProvaAtiva();

        // prova de hardware substitui a aula quando disponível
        if (prova != null && prova.getDisciplina().getTipo() == TipoDisciplina.HARDWARE) {
            acoes.add(new RealizarProva(prova));
        } else {
            acoes.add(new CursarDisciplina());
        }
        return acoes;
    }
}