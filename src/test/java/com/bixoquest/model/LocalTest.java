// LocalTest.java
package com.bixoquest.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LocalTest {

    // Jogo necessário pois Local agora depende dele para verificar provaAtiva
    private Jogo jogo = new Jogo();

    @Test
    void pontoDeOnibusDeveRetornarNomeCorreto() {
        assertEquals("Ponto de Ônibus", new PontoDeOnibus(jogo).getNome());
    }

    @Test
    void salaDeAulaDeveRetornarNomeCorreto() {
        assertEquals("Sala de Aula", new SalaDeAula(jogo).getNome());
    }

    @Test
    void laboratorioDeveRetornarNomeCorreto() {
        assertEquals("Laboratório", new Laboratorio(jogo).getNome());
    }

    @Test
    void cantinaDeveRetornarNomeCorreto() {
        assertEquals("Cantina", new Cantina(jogo).getNome());
    }

    @Test
    void colegiadoDeveRetornarNomeCorreto() {
        assertEquals("Colegiado", new Colegiado(jogo).getNome());
    }

    @Test
    void pontoDeOnibusDeveRetornarListaDeAcoes() {
        assertNotNull(new PontoDeOnibus(jogo).getAcoesDisponiveis());
    }

    @Test
    void salaDeAulaDeveRetornarCursarDisciplinaSemProvaAtiva() {
        SalaDeAula local = new SalaDeAula(jogo);
        assertTrue(local.getAcoesDisponiveis().get(0) instanceof CursarDisciplina);
    }

    @Test
    void salaDeAulaDeveRetornarRealizarProvaComProvaAtiva() {
        Disciplina disc = new Disciplina("Software 1",
                com.bixoquest.enums.TipoDisciplina.SOFTWARE, null);
        disc.setEstado(com.bixoquest.enums.EstadoDisciplina.CURSANDO);
        EventoProva prova = new EventoProva(disc, 1.0, 70, 30, 1, 100);
        jogo.setProvaAtiva(prova);

        SalaDeAula local = new SalaDeAula(jogo);
        assertTrue(local.getAcoesDisponiveis().get(0) instanceof RealizarProva);
    }

    @Test
    void laboratorioDeveRetornarListaDeAcoes() {
        assertNotNull(new Laboratorio(jogo).getAcoesDisponiveis());
    }

    @Test
    void cantinaDeveRetornarListaDeAcoes() {
        assertNotNull(new Cantina(jogo).getAcoesDisponiveis());
    }

    @Test
    void colegiadoDeveRetornarListaDeAcoes() {
        assertNotNull(new Colegiado(jogo).getAcoesDisponiveis());
    }
}