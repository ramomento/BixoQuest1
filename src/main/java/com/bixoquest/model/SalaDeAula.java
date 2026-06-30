package com.bixoquest.model;
import com.bixoquest.enums.TipoDisciplina;
import java.util.ArrayList;
import java.util.List;

public class SalaDeAula extends Local {
    public SalaDeAula(Jogo jogo) {
        super("Sala de Aula", jogo);
    }

    @Override
    public List<Acao> getAcoesDisponiveis() {
        List<Acao> acoes = new ArrayList<>();
        EventoProva prova = getJogo().getProvaAtiva();

        System.out.println("[DEBUG] SalaDeAula - getProvaAtiva() = " + prova);
        if (prova != null) {
            System.out.println("[DEBUG] Tipo da prova: " + prova.getDisciplina().getTipo());
        }

        // prova de software substitui a aula quando disponível
        if (prova != null && prova.getDisciplina().getTipo() == TipoDisciplina.SOFTWARE) {
            acoes.add(new RealizarProva(prova));
        } else {
            acoes.add(new CursarDisciplina());
        }
        return acoes;
    }
}