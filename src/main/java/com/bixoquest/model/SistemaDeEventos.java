package com.bixoquest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SistemaDeEventos {
    private List<Evento> eventosFixos;
    private List<Evento> eventosAleatorios;

    public SistemaDeEventos(){
        this.eventosFixos = new ArrayList<>();
        this.eventosAleatorios = new ArrayList<>();
    }

    public void processarEventos(int turnoAtual, Aluno aluno, Jogo jogo){
        // apenas eventos fixos são processados automaticamente por turno
        for(Evento evento: eventosFixos){
            if(evento.deveExecutar(turnoAtual)){
                evento.aplicar(aluno, jogo);
            }
        }
    }

    public Evento sortearEventoAleatorio(){
        if(eventosAleatorios.isEmpty()) return null;
        Random random = new Random();
        return eventosAleatorios.get(random.nextInt(eventosAleatorios.size()));
    }

    public void adicionarEventoFixo(Evento evento){ eventosFixos.add(evento); }
    public void adicionarEventoAleatorio(Evento evento){ eventosAleatorios.add(evento); }
    public List<Evento> getEventosFixos() { return eventosFixos; }
    public List<Evento> getEventosAleatorios(){ return eventosAleatorios; }
}