package com.bixoquest.model;

import com.bixoquest.enums.EstadoDisciplina;
import com.bixoquest.enums.TipoDisciplina;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private String nome;
    private List<Double> notas = new ArrayList<>();
    private EstadoDisciplina estado;
    private TipoDisciplina tipo;
    private Disciplina preRequisito;

    public Disciplina(String nome, TipoDisciplina tipo, Disciplina preRequisito){
        this.nome = nome;
        this.tipo = tipo;
        this.preRequisito = preRequisito;
        // disciplinas sem pré-requisito são as iniciais da trilha
        this.estado = (preRequisito == null) ? EstadoDisciplina.DISPONIVEL : EstadoDisciplina.BLOQUEADA;
    }

    public void adicionarNota(double valor){
        // máximo de 3 notas por disciplina
        if(notas.size() >= 3) return;
        if(valor < 0 || valor > 10) return;
        if(estado != EstadoDisciplina.CURSANDO) return;
        notas.add(valor);
    }

    public double calcularMedia(){
        if(notas.isEmpty()) return 0;
        double media = 0.0;
        for(double nota : notas) media += nota;
        return media / notas.size();
    }

    public boolean estaAprovada(){
        // atualiza o estado ao avaliar — chamado ao fim do semestre
        if (calcularMedia() >= 7) {
            estado = EstadoDisciplina.APROVADO;
            return true;
        }
        estado = EstadoDisciplina.REPROVADO;
        return false;
    }

    public boolean podeSerCursada(){
        if(preRequisito == null) return true;
        // verifica estado diretamente para evitar efeito colateral de estaAprovada()
        return preRequisito.getEstado() == EstadoDisciplina.APROVADO;
    }

    public List<Double> getNotas(){ return notas; }
    public String getNome(){ return nome; }
    public EstadoDisciplina getEstado() { return estado; }
    public Disciplina getPreRequisito() { return preRequisito; }
    public TipoDisciplina getTipo() { return tipo; }
    public void setEstado(EstadoDisciplina estado) { this.estado = estado; }
}