package com.bixoquest.model;

public class EventoCantinaCheia extends Evento {

    public EventoCantinaCheia() {
        setAtivo(true);
    }

    @Override
    public void aplicar(Aluno aluno, Jogo jogo) {
        jogo.setCantinaOcupada(true);
        System.out.println("A cantina está com uma fila enorme — não dá pra entrar agora!");
    }
}