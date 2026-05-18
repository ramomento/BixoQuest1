package com.bixoquest.model;

import com.bixoquest.enums.EstadoDisciplina;
import java.util.Random;

public class RealizarProva extends Acao {
    private EventoProva eventoProva;

    public RealizarProva(EventoProva eventoProva) {
        this.eventoProva = eventoProva;
    }

    @Override
    public void executarAcao(Aluno aluno, Jogo jogo) {
        double nota = calcularNota(aluno);
        eventoProva.getDisciplina().adicionarNota(nota);
        eventoProva.setRealizada(true);
        jogo.setProvaAtiva(null); // limpa prova ativa após realização
    }
    @Override
    public boolean podeExecutar(Aluno aluno) {
        // prova só pode ser realizada uma vez e a disciplina deve estar em curso
        return !eventoProva.isRealizada() &&
                eventoProva.getDisciplina().getEstado() == EstadoDisciplina.CURSANDO;
    }

    public double calcularNota(Aluno aluno) {
        // passo 1: conhecimento efetivo ponderado pelos pesos da prova
        double conhecimentoEfetivo =
                (aluno.getConhecimentoTeorico() * eventoProva.getPesoTeorico() / 100.0) +
                        (aluno.getConhecimentoPratico() * eventoProva.getPesoPratico() / 100.0);

        // passo 2: percentual em relação ao esperado → nota base de 0 a 10
        double percentual = Math.min(conhecimentoEfetivo / eventoProva.getConhecimentoEsperado(), 1.0);
        double nota = percentual * 10;

        // passo 3: aplica eventos aleatórios
        nota = aplicarEventosAleatorios(nota, aluno);

        // passo 4: garante que a nota permaneça entre 0 e 10
        return Math.max(0, Math.min(10, nota));
    }

    private double aplicarEventosAleatorios(double nota, Aluno aluno) {
        Random random = new Random();

        // evento negativo tem prioridade — verifica primeiro
        double chanceNegativa = calcularChanceNegativa(aluno);
        if (random.nextDouble() < chanceNegativa) {
            // redução entre 5% e 90% da nota
            double reducao = 0.05 + random.nextDouble() * 0.85;
            return nota * (1 - reducao);
        }

        // milagre acadêmico só ocorre se evento negativo não disparou
        double chancePositiva = calcularChancePositiva(aluno);
        if (random.nextDouble() < chancePositiva) {
            // bônus entre 20% e 60% da nota
            double bonus = 0.20 + random.nextDouble() * 0.40;
            return nota * (1 + bonus);
        }

        return nota;
    }

    private double calcularChanceNegativa(Aluno aluno) {
        double chance = switch (aluno.getSaude()) {
            case SAUDAVEL -> 0.05;
            case CANSADO  -> 0.20;
            case DOENTE   -> 0.40;
        };

        chance += switch (aluno.getMotivacao()) {
            case ALTA  -> -0.05;
            case NORMAL -> 0.0;
            case BAIXA -> 0.15;
        };

        // energia baixa aumenta risco de evento negativo
        int energia = aluno.getEnergia();
        if (energia <= 2)      chance += 0.15;
        else if (energia <= 6) chance += 0.05;

        // chance nunca negativa — SAUDAVEL + ALTA resultaria em -0.00
        return Math.max(0, chance);
    }

    private double calcularChancePositiva(Aluno aluno) {
        return switch (aluno.getMotivacao()) {
            case ALTA   -> 0.08;
            case NORMAL -> 0.05;
            case BAIXA  -> 0.02;
        };
    }

    public EventoProva getEventoProva() { return eventoProva; }
}