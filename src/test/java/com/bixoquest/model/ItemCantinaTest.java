// ItemCantinaTest.java
package com.bixoquest.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemCantinaTest {

    @Test
    void itemDeveEstarDisponivelComQuantidadePositiva() {
        assertTrue(new ItemCantina("Café", 4.0, 5).estaDisponivel());
    }

    @Test
    void itemNaoDeveEstarDisponivelComQuantidadeZero() {
        assertFalse(new ItemCantina("Café", 4.0, 0).estaDisponivel());
    }

    @Test
    void consumirDeveReduzirQuantidade() {
        ItemCantina item = new ItemCantina("Café", 4.0, 5);
        item.consumir();
        assertEquals(4, item.getQuantidadeDisponivel());
    }

    @Test
    void consumirSemEstoqueNaoDeveReduzirAbaixoDeZero() {
        ItemCantina item = new ItemCantina("Café", 4.0, 0);
        item.consumir();
        assertEquals(0, item.getQuantidadeDisponivel());
    }
}