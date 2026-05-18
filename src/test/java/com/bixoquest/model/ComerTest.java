// ComerTest.java
package com.bixoquest.model;

import com.bixoquest.enums.EstadoMotivacao;
import com.bixoquest.enums.EstadoSaude;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ComerTest {

    @Test
    void comerDevePoderExecutarComDinheiroEStoqueDisponivel() {
        assertTrue(new Comer(new ItemCantina("Café", 4.0, 5)).podeExecutar(new Aluno()));
    }

    @Test
    void comerNaoDeveExecutarSemEstoque() {
        assertFalse(new Comer(new ItemCantina("Café", 4.0, 0)).podeExecutar(new Aluno()));
    }

    @Test
    void comerNaoDeveExecutarSemDinheiro() {
        Aluno aluno = new Aluno();
        aluno.gastarDinheiro(200.0);
        assertFalse(new Comer(new ItemCantina("Café", 4.0, 5)).podeExecutar(aluno));
    }

    @Test
    void cafeDeveAumentarEnergia() {
        Aluno aluno = new Aluno();
        aluno.reduzirEnergia(5);
        new Comer(new ItemCantina("Café", 4.0, 5)).executarAcao(aluno, null);
        assertEquals(7, aluno.getEnergia());
    }

    @Test
    void sanduicheDeveAumentarMotivacao() {
        Aluno aluno = new Aluno();
        aluno.reduzirMotivacao();
        new Comer(new ItemCantina("Sanduíche", 8.0, 5)).executarAcao(aluno, null);
        assertEquals(EstadoMotivacao.ALTA, aluno.getMotivacao());
    }

    @Test
    void remedioDeveCurarAlunoDoente() {
        Aluno aluno = new Aluno();
        aluno.alterarSaude(EstadoSaude.DOENTE);
        new Comer(new ItemCantina("Remédio", 10.0, 3)).executarAcao(aluno, null);
        assertEquals(EstadoSaude.SAUDAVEL, aluno.getSaude());
    }

    @Test
    void remedioNaoDeveAlterarSaudeCansado() {
        Aluno aluno = new Aluno();
        aluno.alterarSaude(EstadoSaude.CANSADO);
        new Comer(new ItemCantina("Remédio", 10.0, 3)).executarAcao(aluno, null);
        assertEquals(EstadoSaude.CANSADO, aluno.getSaude());
    }

    @Test
    void tortaDeveAumentarEnergiaEMotivacao() {
        Aluno aluno = new Aluno();
        aluno.reduzirEnergia(5);
        aluno.reduzirMotivacao();
        new Comer(new ItemCantina("Torta", 15.0, 3)).executarAcao(aluno, null);
        assertAll(
                () -> assertEquals(7, aluno.getEnergia()),
                () -> assertEquals(EstadoMotivacao.ALTA, aluno.getMotivacao())
        );
    }
}