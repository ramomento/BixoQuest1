package com.bixoquest.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Evento {
    private int turnoAplicacao;
    private boolean ativo;

    // Observers (listeners) que serão notificados
    protected List<EventoListener> listeners = new ArrayList<>();

    public abstract void aplicar(Aluno aluno, Jogo jogo);

    /**
     * Verifica se o evento deve ser executado neste turno.
     */
    public boolean deveExecutar(int turnoAtual){
        return ativo && turnoAtual == turnoAplicacao;
    }

    /**
     * Registra um listener para ser notificado quando este evento for aplicado.
     */
    public void adicionarListener(EventoListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Remove um listener.
     */
    public void removerListener(EventoListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifica todos os listeners que o evento foi aplicado.
     */
    protected void notificarAplicado(Aluno aluno) {
        System.out.println("[OBSERVER] Notificando " + listeners.size() + " listener(s)");
        for (EventoListener listener : listeners) {
            listener.aoEventoAplicado(this, aluno);
        }
    }

    /**
     * Notifica todos os listeners que o evento foi descartado.
     */
    protected void notificarDescartado() {
        for (EventoListener listener : listeners) {
            listener.aoEventoDescartado(this);
        }
    }

    // Getters e Setters
    public int getTurnoAplicacao() { return turnoAplicacao; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public void setTurnoAplicacao(int turnoAplicacao) { this.turnoAplicacao = turnoAplicacao; }
}