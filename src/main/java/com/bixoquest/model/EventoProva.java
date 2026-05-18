package com.bixoquest.model;

public class EventoProva extends Evento {
    private Disciplina disciplina;
    private double peso;
    private boolean realizada;
    private double pesoTeorico;
    private double pesoPratico;
    private int conhecimentoEsperado;

    public EventoProva(Disciplina disciplina, double peso, double pesoTeorico,
                       double pesoPratico, int turnoAplicacao, int conhecimentoEsperado){
        this.disciplina = disciplina;
        this.peso = peso;
        this.pesoTeorico = pesoTeorico;
        this.pesoPratico = pesoPratico;
        this.conhecimentoEsperado = conhecimentoEsperado;
        this.realizada = false;
        setTurnoAplicacao(turnoAplicacao);
        setAtivo(true);
    }

    @Override
    public void aplicar(Aluno aluno, Jogo jogo) {
        jogo.setProvaAtiva(this);
    }

    public Disciplina getDisciplina() { return disciplina; }
    public double getPeso() { return peso; }
    public double getPesoTeorico() { return pesoTeorico; }
    public double getPesoPratico() { return pesoPratico; }
    public int getConhecimentoEsperado() { return conhecimentoEsperado; }
    public boolean isRealizada() { return realizada; }
    public void setRealizada(boolean realizada) { this.realizada = realizada; }
}