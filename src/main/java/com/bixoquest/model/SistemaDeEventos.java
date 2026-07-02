package com.bixoquest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Gerenciador de eventos do jogo.
 * Implementa o padrão Observer para notificar listeners sobre eventos.
 */
public class SistemaDeEventos {
    private List<Evento> eventosFixos;
    private List<Evento> eventosAleatorios;
    private List<EventoListener> listeners;  // ← Novo: observers globais

    public SistemaDeEventos(){
        this.eventosFixos = new ArrayList<>();
        this.eventosAleatorios = new ArrayList<>();
        this.listeners = new ArrayList<>();
    }

    /**
     * Processa eventos fixos neste turno.
     * Notifica listeners quando eventos são aplicados.
     */
    public void processarEventos(int turnoAtual, Aluno aluno, Jogo jogo){
        for(Evento evento: eventosFixos){
            if(evento.deveExecutar(turnoAtual)){
                System.out.println("[SISTEMA] Evento " + evento.getClass().getSimpleName()
                        + " aplicado no turno " + turnoAtual);
                evento.aplicar(aluno, jogo);
                // Notificação já ocorre dentro de evento.aplicar() via notificarAplicado()
            }
        }
    }

    /**
     * Sorteia um evento aleatório da lista.
     */
    public Evento sortearEventoAleatorio(){
        if(eventosAleatorios.isEmpty()) return null;
        Random random = new Random();
        return eventosAleatorios.get(random.nextInt(eventosAleatorios.size()));
    }

    /**
     * Registra um listener global que recebe notificações de todos os eventos.
     */
    public void adicionarListener(EventoListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
            System.out.println("[OBSERVER] Listener adicionado. Total: " + listeners.size());
        }
    }

    /**
     * Remove um listener global.
     */
    public void removerListener(EventoListener listener) {
        listeners.remove(listener);
        System.out.println("[OBSERVER] Listener removido. Total: " + listeners.size());
    }

    /**
     * Notifica todos os listeners globais sobre um evento.
     */
    public void notificarListeners(Evento evento, Aluno aluno) {
        for (EventoListener listener : listeners) {
            listener.aoEventoAplicado(evento, aluno);
        }
    }

    // Getters e Setters
    public void adicionarEventoFixo(Evento evento){ eventosFixos.add(evento); }
    public void adicionarEventoAleatorio(Evento evento){ eventosAleatorios.add(evento); }
    public List<Evento> getEventosFixos() { return eventosFixos; }
    public List<Evento> getEventosAleatorios(){ return eventosAleatorios; }
    public List<EventoListener> getListeners() { return listeners; }
}