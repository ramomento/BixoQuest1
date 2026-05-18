package com.bixoquest.model;

import com.bixoquest.enums.EstadoDisciplina;
import com.bixoquest.enums.TipoDisciplina;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SemestreTest {

    private List<Disciplina> criarDisciplinas() {
        List<Disciplina> disciplinas = new ArrayList<>();
        disciplinas.add(new Disciplina("Software 1", TipoDisciplina.SOFTWARE, null));
        disciplinas.add(new Disciplina("Hardware 1", TipoDisciplina.HARDWARE, null));
        return disciplinas;
    }

    @Test
    void semestreDeveIniciarNoTurno1() {
        Semestre semestre = new Semestre(1, criarDisciplinas());
        assertEquals(1, semestre.getTurnoAtual());
    }

    @Test
    void avancarTurnoDeveIncrementarTurno() {
        Semestre semestre = new Semestre(1, criarDisciplinas());
        semestre.avancarTurno();
        assertEquals(2, semestre.getTurnoAtual());
    }

    @Test
    void terminouSemestreDeveRetornarTrueNoUltimoTurno() {
        Semestre semestre = new Semestre(1, criarDisciplinas());
        for (int i = 0; i < 6; i++) semestre.avancarTurno();
        assertTrue(semestre.terminouSemestre());
    }

    @Test
    void naoDeveAvancarAlemDoUltimoTurno() {
        Semestre semestre = new Semestre(1, criarDisciplinas());
        for (int i = 0; i < 10; i++) semestre.avancarTurno();
        assertEquals(6, semestre.getTurnoAtual());
    }

    @Test
    void avaliarDisciplinasDeveAprovarComMediaSuficiente() {
        Aluno aluno = new Aluno();
        Disciplina disc = new Disciplina("Software 1", TipoDisciplina.SOFTWARE, null);
        disc.setEstado(EstadoDisciplina.CURSANDO);
        disc.adicionarNota(8.0);
        disc.adicionarNota(7.0);
        disc.adicionarNota(9.0);

        List<Disciplina> disciplinas = new ArrayList<>();
        disciplinas.add(disc);

        Semestre semestre = new Semestre(1, disciplinas);
        semestre.avaliarDisciplinas(aluno);

        assertEquals(EstadoDisciplina.APROVADO, disc.getEstado());
    }

    @Test
    void avaliarDisciplinasDeveReprovarComMediaInsuficiente() {
        Aluno aluno = new Aluno();
        Disciplina disc = new Disciplina("Software 1", TipoDisciplina.SOFTWARE, null);
        disc.setEstado(EstadoDisciplina.CURSANDO);
        disc.adicionarNota(3.0);
        disc.adicionarNota(4.0);
        disc.adicionarNota(2.0);

        List<Disciplina> disciplinas = new ArrayList<>();
        disciplinas.add(disc);

        Semestre semestre = new Semestre(1, disciplinas);
        semestre.avaliarDisciplinas(aluno);

        assertEquals(EstadoDisciplina.REPROVADO, disc.getEstado());
    }
}