package com.bixoquest.model;

import com.bixoquest.enums.EstadoDisciplina;
import com.bixoquest.enums.EstadoSaude;
import com.bixoquest.enums.TipoDisciplina;

import java.util.ArrayList;
import java.util.List;

public class Jogo {
    private int turnoAtual;
    private Aluno aluno;
    private SistemaDeEventos sistemaDeEventos;
    private Semestre semestreAtual;
    private List<Disciplina> disciplinasDisponiveis;
    private EventoProva provaAtiva;
    private boolean cantinaOcupada;

    public Jogo(){
        this.turnoAtual = 1;
        this.aluno = new Aluno();
        this.sistemaDeEventos = new SistemaDeEventos();
        // disciplinas geradas antes do semestre pois o semestre depende delas
        this.disciplinasDisponiveis = gerarDisciplinas();
        this.semestreAtual = new Semestre(1, gerarDisciplinasParaSemestre());
        agendarProvasSemestre();
    }

    private List<Disciplina> gerarDisciplinas(){
        List<Disciplina> disciplinas = new ArrayList<>();

        // trilha hardware — encadeamento por pré-requisito
        Disciplina hardware1 = new Disciplina("Hardware 1", TipoDisciplina.HARDWARE, null);
        Disciplina hardware2 = new Disciplina("Hardware 2", TipoDisciplina.HARDWARE, hardware1);
        Disciplina hardware3 = new Disciplina("Hardware 3", TipoDisciplina.HARDWARE, hardware2);
        Disciplina hardware4 = new Disciplina("Hardware 4", TipoDisciplina.HARDWARE, hardware3);
        Disciplina hardware5 = new Disciplina("Hardware 5", TipoDisciplina.HARDWARE, hardware4);

        // trilha software — encadeamento por pré-requisito
        Disciplina software1 = new Disciplina("Software 1", TipoDisciplina.SOFTWARE, null);
        Disciplina software2 = new Disciplina("Software 2", TipoDisciplina.SOFTWARE, software1);
        Disciplina software3 = new Disciplina("Software 3", TipoDisciplina.SOFTWARE, software2);
        Disciplina software4 = new Disciplina("Software 4", TipoDisciplina.SOFTWARE, software3);
        Disciplina software5 = new Disciplina("Software 5", TipoDisciplina.SOFTWARE, software4);

        disciplinas.add(hardware1); disciplinas.add(hardware2);
        disciplinas.add(hardware3); disciplinas.add(hardware4);
        disciplinas.add(hardware5); disciplinas.add(software1);
        disciplinas.add(software2); disciplinas.add(software3);
        disciplinas.add(software4); disciplinas.add(software5);

        return disciplinas;
    }

    public List<Disciplina> gerarDisciplinasParaSemestre(){
        List<Disciplina> paraSemestre = new ArrayList<>();

        for (Disciplina disciplina : disciplinasDisponiveis){
            // seleciona apenas disciplinas que o aluno pode e ainda não começou
            if(disciplina.podeSerCursada()){
                // Se reprovou, reseta para DISPONÍVEL e limpa as notas
                if(disciplina.getEstado() == EstadoDisciplina.REPROVADO){
                    disciplina.setEstado(EstadoDisciplina.DISPONIVEL);
                    disciplina.getNotas().clear();  // ← Limpa notas anteriores
                }

                // Só adiciona se está DISPONÍVEL
                if(disciplina.getEstado() == EstadoDisciplina.DISPONIVEL){
                    paraSemestre.add(disciplina);
                    disciplina.setEstado(EstadoDisciplina.CURSANDO);
                }
            }
        }
        return paraSemestre;
    }

    public boolean isCantinaOcupada(){return cantinaOcupada;}

    private void agendarProvasSemestre() {
        sistemaDeEventos = new SistemaDeEventos();
        int conhecimentoBase = 200 * semestreAtual.getNumero();

        for (Disciplina d : semestreAtual.getDisciplinas()) {
            int[] turnos = d.getTipo() == TipoDisciplina.HARDWARE ? new int[]{2, 4} : new int[]{3, 5};
            double pesoTeorico = d.getTipo() == TipoDisciplina.SOFTWARE ? 70 : 30;
            double pesoPratico = d.getTipo() == TipoDisciplina.SOFTWARE ? 30 : 70;

            for (int i = 0; i < turnos.length; i++) {
                int conhecimentoEsperado = i == 0 ? conhecimentoBase : (int)(conhecimentoBase * 1.25);
                int turnoGlobal = (semestreAtual.getNumero() - 1) * 6 + turnos[i];
                EventoProva prova = new EventoProva(d, 1.0, pesoTeorico, pesoPratico, turnoGlobal, conhecimentoEsperado);
                sistemaDeEventos.adicionarEventoFixo(prova);
            }
        }
    }

    public void finalizarTurno(){
        sistemaDeEventos.processarEventos(turnoAtual, aluno, this);
        aluno.resetarConselhosUsados(); // reseta conselhos a cada turno
        semestreAtual.avancarTurno();
        cantinaOcupada = false; // Reseta do turno anterior

        // Sorteio de EventoCantinaCheia (5% de chance)
        if (Math.random() < 0.05) {
            cantinaOcupada = true;
        }

        if (semestreAtual.terminouSemestre()) {
            semestreAtual.avaliarDisciplinas(aluno);
            for (Disciplina d : disciplinasDisponiveis) {
                if (d.getEstado() == EstadoDisciplina.BLOQUEADA && d.podeSerCursada()) {
                    d.setEstado(EstadoDisciplina.DISPONIVEL);
                }
            }

            int proximoNumero = semestreAtual.getNumero() + 1;
            semestreAtual = new Semestre(proximoNumero, gerarDisciplinasParaSemestre());
            aluno.adicionarDinheiro(200.0);
            agendarProvasSemestre();
            provaAtiva = null;

        }

        if (aluno.getSaude() == EstadoSaude.CANSADO) {
            aluno.setEnergia(5);
        } else {
            aluno.setEnergia(10);
        }
        aluno.gastarDinheiro(10.0);
        turnoAtual++;
    }

    public int getTurnoAtual(){ return turnoAtual; }
    public Aluno getAluno() { return aluno; }
    public Semestre getSemestreAtual() { return semestreAtual; }
    public SistemaDeEventos getSistemaDeEventos() { return sistemaDeEventos; }
    public List<Disciplina> getDisciplinasDisponiveis() { return disciplinasDisponiveis; }


    public void setCantinaOcupada(boolean ocupada){ this.cantinaOcupada = ocupada;}
    public void setTurnoAtual(int turno) { this.turnoAtual = turno; }
    public void setSemestreAtual(Semestre semestre) { this.semestreAtual = semestre; }
    public void setProvaAtiva(EventoProva prova) { this.provaAtiva = prova; }
    public EventoProva getProvaAtiva() { return provaAtiva; }
}