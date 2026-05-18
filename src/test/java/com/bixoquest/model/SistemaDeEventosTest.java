// SistemaDeEventosTest.java
package com.bixoquest.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SistemaDeEventosTest {

    @Test
    void sistemaDeveIniciarComListasVazias() {
        SistemaDeEventos sistema = new SistemaDeEventos();
        assertTrue(sistema.getEventosFixos().isEmpty());
        assertTrue(sistema.getEventosAleatorios().isEmpty());
    }

    @Test
    void adicionarEventoFixoDeveAdicionarNaLista() {
        SistemaDeEventos sistema = new SistemaDeEventos();
        sistema.adicionarEventoFixo(criarEventoProva(2));
        assertEquals(1, sistema.getEventosFixos().size());
    }

    @Test
    void adicionarEventoAleatorioDeveAdicionarNaLista() {
        SistemaDeEventos sistema = new SistemaDeEventos();
        sistema.adicionarEventoAleatorio(new EventoAleatorio());
        assertEquals(1, sistema.getEventosAleatorios().size());
    }

    @Test
    void sortearEventoAleatorioComListaVaziaDeveRetornarNull() {
        assertNull(new SistemaDeEventos().sortearEventoAleatorio());
    }

    @Test
    void sortearEventoAleatorioDeveRetornarEventoDaLista() {
        SistemaDeEventos sistema = new SistemaDeEventos();
        sistema.adicionarEventoAleatorio(new EventoAleatorio());
        assertNotNull(sistema.sortearEventoAleatorio());
    }

    // construtor atualizado com conhecimentoEsperado
    private EventoProva criarEventoProva(int turno) {
        Disciplina disc = new Disciplina("Software 1",
                com.bixoquest.enums.TipoDisciplina.SOFTWARE, null);
        return new EventoProva(disc, 1.0, 70, 30, turno, 100);
    }
}