package com.bixoquest.model;

import java.util.List;

public class Semestre {
    private int numero;
    private List<Disciplina> disciplinas;
    private int turnoAtual;
    private int totalTurnos;

    public Semestre(int numero, List<Disciplina> disciplinas){
        this.numero = numero;
        this.disciplinas = disciplinas;
        this.turnoAtual = 1;
        // semestre fixo de 6 turnos
        this.totalTurnos = 6;
    }

    public void avancarTurno(){
        if(!terminouSemestre()) turnoAtual++;
    }

    public boolean terminouSemestre(){
        return turnoAtual > totalTurnos;
    }

    public void avaliarDisciplinas(Aluno aluno){
        // estaAprovada() atualiza o estado de cada disciplina internamente
        for(Disciplina disciplina : disciplinas){
            disciplina.estaAprovada();
        }
    }

    public int getNumero(){ return numero; }
    public int getTurnoAtual(){ return turnoAtual; }
    public int getTotalTurnos(){ return totalTurnos; }
    public List<Disciplina> getDisciplinas(){ return disciplinas; }

    public void setTurnoAtual(int turno) { this.turnoAtual = turno; }

}