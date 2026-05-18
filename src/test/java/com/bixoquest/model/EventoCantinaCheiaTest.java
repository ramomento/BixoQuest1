// EventoCantinaCheiaTest.java
package com.bixoquest.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventoCantinaCheiaTest {

    @Test
    void eventoDeveMarcarCantinaComoOcupada() {
        Jogo jogo = new Jogo();
        new EventoCantinaCheia().aplicar(jogo.getAluno(), jogo);
        assertTrue(jogo.isCantinaOcupada());
    }

    @Test
    void cantinaDeveEstarLivreAoIniciarTurno() {
        Jogo jogo = new Jogo();
        jogo.setCantinaOcupada(true);
        jogo.finalizarTurno();
        assertFalse(jogo.isCantinaOcupada());
    }
}