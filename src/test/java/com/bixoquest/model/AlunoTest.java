package com.bixoquest.model;

import com.bixoquest.enums.EstadoMotivacao;
import com.bixoquest.enums.EstadoSaude;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AlunoTest {

    @Test
    void alunoDeveIniciarComValoresPadrao() {
        Aluno aluno = new Aluno();
        assertEquals(10, aluno.getEnergia());
        assertEquals(200.0, aluno.getDinheiro());
        assertEquals(EstadoSaude.SAUDAVEL, aluno.getSaude());
        assertEquals(EstadoMotivacao.ALTA, aluno.getMotivacao());
        assertEquals(0, aluno.getConhecimentoTeorico());
        assertEquals(0, aluno.getConhecimentoPratico());
    }

    @Test
    void aumentarEnergiaDeveIncrementarEnergia() {
        Aluno aluno = new Aluno();
        aluno.reduzirEnergia(1);
        aluno.aumentarEnergia();
        assertEquals(10, aluno.getEnergia());
    }

    @Test
    void energiaNaoDeveUltrapassarDez() {
        Aluno aluno = new Aluno();
        aluno.aumentarEnergia();
        assertEquals(10, aluno.getEnergia());
    }

    @Test
    void reduzirEnergiaDeveReduzirEnergia() {
        Aluno aluno = new Aluno();
        aluno.reduzirEnergia(3);
        assertEquals(7, aluno.getEnergia());
    }

    @Test
    void energiaNaoDeveSerNegativa() {
        Aluno aluno = new Aluno();
        aluno.reduzirEnergia(15);
        assertEquals(0, aluno.getEnergia());
    }

    @Test
    void estaSemEnergiaDeveRetornarTrueQuandoEnergiaZero() {
        Aluno aluno = new Aluno();
        aluno.reduzirEnergia(10);
        assertTrue(aluno.estaSemEnergia());
    }

    @Test
    void aumentarMotivacaoDeveSubirUmNivel() {
        Aluno aluno = new Aluno();
        aluno.reduzirMotivacao();
        aluno.aumentarMotivacao();
        assertEquals(EstadoMotivacao.ALTA, aluno.getMotivacao());
    }

    @Test
    void reduzirMotivacaoDeveDescerUmNivel() {
        Aluno aluno = new Aluno();
        aluno.reduzirMotivacao();
        assertEquals(EstadoMotivacao.NORMAL, aluno.getMotivacao());
    }

    @Test
    void motivacaoNaoDeveDescerAbaixoDeBaixa() {
        Aluno aluno = new Aluno();
        aluno.reduzirMotivacao();
        aluno.reduzirMotivacao();
        aluno.reduzirMotivacao();
        assertEquals(EstadoMotivacao.BAIXA, aluno.getMotivacao());
    }

    @Test
    void adicionarDinheiroDeveAumentarSaldo() {
        Aluno aluno = new Aluno();
        aluno.adicionarDinheiro(50.0);
        assertEquals(250.0, aluno.getDinheiro());
    }

    @Test
    void gastarDinheiroDeveReduzirSaldo() {
        Aluno aluno = new Aluno();
        aluno.gastarDinheiro(50.0);
        assertEquals(150.0, aluno.getDinheiro());
    }

    @Test
    void gastarDinheiroSemSaldoSuficienteDeveSerIgnorado() {
        Aluno aluno = new Aluno();
        aluno.gastarDinheiro(300.0);
        assertEquals(200.0, aluno.getDinheiro());
    }

    @Test
    void temDinheiroSuficienteDeveRetornarTrueQuandoSaldoSuficiente() {
        Aluno aluno = new Aluno();
        assertTrue(aluno.temDinheiroSuficiente(200.0));
    }

    @Test
    void aumentarConhecimentoTeoricoDeveIncrementar() {
        Aluno aluno = new Aluno();
        aluno.aumentarConhecimentoTeorico(5);
        assertEquals(5, aluno.getConhecimentoTeorico());
    }

    @Test
    void aumentarConhecimentoPraticoDeveIncrementar() {
        Aluno aluno = new Aluno();
        aluno.aumentarConhecimentoPratico(5);
        assertEquals(5, aluno.getConhecimentoPratico());
    }

    @Test
    void adicionarDisciplinaDeveAdicionarNaLista() {
        Aluno aluno = new Aluno();
        Disciplina disc = new Disciplina("Algoritmos 1", com.bixoquest.enums.TipoDisciplina.SOFTWARE, null);
        aluno.adicionarDisciplina(disc);
        assertEquals(1, aluno.getDisciplinas().size());
    }
}