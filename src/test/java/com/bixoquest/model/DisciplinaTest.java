package com.bixoquest.model;

import com.bixoquest.enums.EstadoDisciplina;
import com.bixoquest.enums.TipoDisciplina;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DisciplinaTest {

    @Test
    void disciplinaSemPreRequisitoDeveIniciarComoDisponivel() {
        Disciplina disc = new Disciplina("Algoritmos 1", TipoDisciplina.SOFTWARE, null);
        assertEquals(EstadoDisciplina.DISPONIVEL, disc.getEstado());
    }

    @Test
    void disciplinaComPreRequisitoDeveIniciarComoBloqueada() {
        Disciplina disc1 = new Disciplina("Algoritmos 1", TipoDisciplina.SOFTWARE, null);
        Disciplina disc2 = new Disciplina("Algoritmos 2", TipoDisciplina.SOFTWARE, disc1);
        assertEquals(EstadoDisciplina.BLOQUEADA, disc2.getEstado());
    }

    @Test
    void adicionarNotaValidaDeveAdicionarNaLista() {
        Disciplina disc = new Disciplina("Algoritmos 1", TipoDisciplina.SOFTWARE, null);
        disc.setEstado(EstadoDisciplina.CURSANDO);
        disc.adicionarNota(8.0);
        assertEquals(1, disc.getNotas().size());
    }

    @Test
    void adicionarNotaNegativaDeveSerIgnorada() {
        Disciplina disc = new Disciplina("Algoritmos 1", TipoDisciplina.SOFTWARE, null);
        disc.setEstado(EstadoDisciplina.CURSANDO);
        disc.adicionarNota(-1.0);
        assertEquals(0, disc.getNotas().size());
    }

    @Test
    void adicionarNotaMaiorQueDezDeveSerIgnorada() {
        Disciplina disc = new Disciplina("Algoritmos 1", TipoDisciplina.SOFTWARE, null);
        disc.setEstado(EstadoDisciplina.CURSANDO);
        disc.adicionarNota(11.0);
        assertEquals(0, disc.getNotas().size());
    }

    @Test
    void adicionarQuartaNotaDeveSerIgnorada() {
        Disciplina disc = new Disciplina("Algoritmos 1", TipoDisciplina.SOFTWARE, null);
        disc.setEstado(EstadoDisciplina.CURSANDO);
        disc.adicionarNota(8.0);
        disc.adicionarNota(7.0);
        disc.adicionarNota(9.0);
        disc.adicionarNota(6.0);
        assertEquals(3, disc.getNotas().size());
    }

    @Test
    void adicionarNotaEmDisciplinaNaoCursandoDeveSerIgnorada() {
        Disciplina disc = new Disciplina("Algoritmos 1", TipoDisciplina.SOFTWARE, null);
        disc.adicionarNota(8.0);
        assertEquals(0, disc.getNotas().size());
    }

    @Test
    void calcularMediaSemNotasDeveRetornarZero() {
        Disciplina disc = new Disciplina("Algoritmos 1", TipoDisciplina.SOFTWARE, null);
        assertEquals(0.0, disc.calcularMedia());
    }

    @Test
    void calcularMediaComNotasDeveRetornarMediaCorreta() {
        Disciplina disc = new Disciplina("Algoritmos 1", TipoDisciplina.SOFTWARE, null);
        disc.setEstado(EstadoDisciplina.CURSANDO);
        disc.adicionarNota(8.0);
        disc.adicionarNota(6.0);
        disc.adicionarNota(10.0);
        assertEquals(8.0, disc.calcularMedia());
    }

    @Test
    void estaAprovadaComMediaSuficienteDeveRetornarTrue() {
        Disciplina disc = new Disciplina("Algoritmos 1", TipoDisciplina.SOFTWARE, null);
        disc.setEstado(EstadoDisciplina.CURSANDO);
        disc.adicionarNota(8.0);
        disc.adicionarNota(7.0);
        disc.adicionarNota(9.0);
        assertTrue(disc.estaAprovada());
        assertEquals(EstadoDisciplina.APROVADO, disc.getEstado());
    }

    @Test
    void estaAprovadaComMediaInsuficienteDeveRetornarFalse() {
        Disciplina disc = new Disciplina("Algoritmos 1", TipoDisciplina.SOFTWARE, null);
        disc.setEstado(EstadoDisciplina.CURSANDO);
        disc.adicionarNota(4.0);
        disc.adicionarNota(5.0);
        disc.adicionarNota(3.0);
        assertFalse(disc.estaAprovada());
        assertEquals(EstadoDisciplina.REPROVADO, disc.getEstado());
    }

    @Test
    void podeSerCursadaSemPreRequisitoDeveRetornarTrue() {
        Disciplina disc = new Disciplina("Algoritmos 1", TipoDisciplina.SOFTWARE, null);
        assertTrue(disc.podeSerCursada());
    }

    @Test
    void podeSerCursadaComPreRequisitoAprovadoDeveRetornarTrue() {
        Disciplina disc1 = new Disciplina("Algoritmos 1", TipoDisciplina.SOFTWARE, null);
        disc1.setEstado(EstadoDisciplina.APROVADO);
        Disciplina disc2 = new Disciplina("Algoritmos 2", TipoDisciplina.SOFTWARE, disc1);
        assertTrue(disc2.podeSerCursada());
    }

    @Test
    void podeSerCursadaComPreRequisitoNaoAprovadoDeveRetornarFalse() {
        Disciplina disc1 = new Disciplina("Algoritmos 1", TipoDisciplina.SOFTWARE, null);
        Disciplina disc2 = new Disciplina("Algoritmos 2", TipoDisciplina.SOFTWARE, disc1);
        assertFalse(disc2.podeSerCursada());
    }
}