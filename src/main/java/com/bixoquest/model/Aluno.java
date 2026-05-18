package com.bixoquest.model;

import com.bixoquest.enums.EstadoMotivacao;
import com.bixoquest.enums.EstadoSaude;

import java.util.ArrayList;
import java.util.List;

public class Aluno {
    private int energia;
    private EstadoSaude saude;
    private EstadoMotivacao motivacao;
    private int desempenhoAcademico;
    private double dinheiro;
    private int conhecimentoTeorico;
    private int conhecimentoPratico;
    private int progressoHardware;
    private int progressoSoftware;
    private List<Disciplina> disciplinas;
    private Local localAtual;
    private int conselhosUsados;
    private int interacoesAnimais;

    public Aluno(){
        this.energia = 10;
        this.saude = EstadoSaude.SAUDAVEL;
        this.motivacao = EstadoMotivacao.ALTA;
        this.dinheiro = 200.0;
        this.conhecimentoPratico = 0;
        this.conhecimentoTeorico = 0;
        this.disciplinas = new ArrayList<>();
        // localAtual será definido quando o aluno se mover pela primeira vez
        this.localAtual = null;
        this.conselhosUsados = 0;
        this.desempenhoAcademico = 0;
        this.progressoHardware = 0;
        this.progressoSoftware = 0;
        this.interacoesAnimais = 0;

    }

    public void aumentarEnergia() {
        if (energia < 10) energia++;
    }

    public void reduzirEnergia(int custo) {
        energia -= custo;
        // impede energia negativa
        if (energia < 0) energia = 0;
    }

    public boolean estaSemEnergia() {
        return energia <= 0;
    }

    public void aumentarMotivacao() {
        // motivação sobe um nível por vez
        if (motivacao == EstadoMotivacao.BAIXA) motivacao = EstadoMotivacao.NORMAL;
        else if (motivacao == EstadoMotivacao.NORMAL) motivacao = EstadoMotivacao.ALTA;
    }

    public void reduzirMotivacao() {
        // motivação desce um nível por vez
        if (motivacao == EstadoMotivacao.ALTA) motivacao = EstadoMotivacao.NORMAL;
        else if (motivacao == EstadoMotivacao.NORMAL) motivacao = EstadoMotivacao.BAIXA;
    }

    public void alterarSaude(EstadoSaude estado) { this.saude = estado; }

    public void adicionarDinheiro(double valor) {
        if (valor > 0) dinheiro += valor;
    }

    public void gastarDinheiro(double valor) {
        // só gasta se tiver saldo suficiente
        if (temDinheiroSuficiente(valor)) dinheiro -= valor;
    }

    public boolean temDinheiroSuficiente(double valor) { return dinheiro >= valor; }

    public void aumentarConhecimentoTeorico(int valor) {
        if (valor > 0) conhecimentoTeorico += valor;
    }

    public void aumentarConhecimentoPratico(int valor) {
        if (valor > 0) conhecimentoPratico += valor;
    }

    public void moverPara(Local local) { this.localAtual = local; }
    public void adicionarDisciplina(Disciplina disciplina) { disciplinas.add(disciplina); }

    public void incrementarConselhosUsados() {
        conselhosUsados++;
    }
    public void resetarConselhosUsados() {
        conselhosUsados = 0;
    }

    public void incrementarInteracoesAnimais() { interacoesAnimais++; }
    public void resetarInteracoesAnimais() { interacoesAnimais = 0; }

    public List<Disciplina> getDisciplinas() { return disciplinas; }
    public EstadoSaude getSaude() { return saude; }
    public EstadoMotivacao getMotivacao() { return motivacao; }
    public int getEnergia() { return energia; }
    public double getDinheiro() { return dinheiro; }
    public int getConhecimentoTeorico() { return conhecimentoTeorico; }
    public int getConhecimentoPratico() { return conhecimentoPratico; }
    public int getDesempenhoAcademico() { return desempenhoAcademico; }
    public int getProgressoHardware() { return progressoHardware; }
    public int getProgressoSoftware() { return progressoSoftware; }
    public Local getLocalAtual() { return localAtual; }
    public int getConselhosUsados() { return conselhosUsados; }
    public int getInteracoesAnimais() { return interacoesAnimais; }


    public void alterarMotivacao(EstadoMotivacao estado) { this.motivacao = estado; }
    public void setConselhosUsados(int valor) { this.conselhosUsados = valor; }
    public void setDesempenhoAcademico(int valor) { this.desempenhoAcademico = valor; }
    public void setProgressoHardware(int valor) { this.progressoHardware = valor; }
    public void setProgressoSoftware(int valor) { this.progressoSoftware = valor; }
}