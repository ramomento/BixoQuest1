package com.bixoquest.model;

import com.bixoquest.enums.EstadoDisciplina;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JogoTest {

    @Test
    void jogoDeveIniciarNoTurno1() {
        Jogo jogo = new Jogo();
        assertEquals(1, jogo.getTurnoAtual());
    }

    @Test
    void jogoDeveCriarAlunoAoIniciar() {
        Jogo jogo = new Jogo();
        assertNotNull(jogo.getAluno());
    }

    @Test
    void jogoDeveCriarSemestreAoIniciar() {
        Jogo jogo = new Jogo();
        assertNotNull(jogo.getSemestreAtual());
    }

    @Test
    void gerarDisciplinasParaSemestreDeveRetornarDisciplinasDisponiveis() {
        Jogo jogo = new Jogo();
        assertFalse(jogo.getSemestreAtual().getDisciplinas().isEmpty());
    }

    @Test
    void disciplinasIniciaisDevemEstarCursando() {
        Jogo jogo = new Jogo();
        for (Disciplina disc : jogo.getSemestreAtual().getDisciplinas()) {
            assertEquals(EstadoDisciplina.CURSANDO, disc.getEstado());
        }
    }

    @Test
    void finalizarTurnoDeveAvancarTurno() {
        Jogo jogo = new Jogo();
        jogo.finalizarTurno();
        assertEquals(2, jogo.getTurnoAtual());
    }

    @Test
    void finalizarSemestreDeveCriarNovoSemestre() {
        Jogo jogo = new Jogo();
        int numeroSemestre = jogo.getSemestreAtual().getNumero();
        for (int i = 0; i < 6; i++) jogo.finalizarTurno();
        assertEquals(numeroSemestre + 1, jogo.getSemestreAtual().getNumero());
    }
}