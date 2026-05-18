// AcaoTest.java
package com.bixoquest.model;

import com.bixoquest.enums.EstadoDisciplina;
import com.bixoquest.enums.TipoDisciplina;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AcaoTest {

    @Test
    void cursarDisciplinaDevePoderExecutarComEnergiaSuficiente() {
        Aluno aluno = new Aluno();
        CursarDisciplina acao = new CursarDisciplina();
        assertTrue(acao.podeExecutar(aluno));
    }

    @Test
    void cursarDisciplinaNaoDeveExecutarSemEnergia() {
        Aluno aluno = new Aluno();
        aluno.reduzirEnergia(10);
        CursarDisciplina acao = new CursarDisciplina();
        assertFalse(acao.podeExecutar(aluno));
    }

    @Test
    void realizarProvaDevePoderExecutarComCondicoesCorretas() {
        Aluno aluno = new Aluno();
        Disciplina disc = new Disciplina("Software 1", TipoDisciplina.SOFTWARE, null);
        disc.setEstado(EstadoDisciplina.CURSANDO);
        EventoProva evento = new EventoProva(disc, 1.0, 70, 30, 1, 100);
        RealizarProva acao = new RealizarProva(evento);
        assertTrue(acao.podeExecutar(aluno));
    }

    @Test
    void realizarProvaNaoDeveExecutarComProvaJaRealizada() {
        Aluno aluno = new Aluno();
        Disciplina disc = new Disciplina("Software 1", TipoDisciplina.SOFTWARE, null);
        disc.setEstado(EstadoDisciplina.CURSANDO);
        EventoProva evento = new EventoProva(disc, 1.0, 70, 30, 1, 100);
        evento.setRealizada(true);
        RealizarProva acao = new RealizarProva(evento);
        assertFalse(acao.podeExecutar(aluno));
    }

    @Test
    void pedirConselhoDevePoderExecutarComConselhosDisponiveis() {
        Aluno aluno = new Aluno();
        PedirConselho acao = new PedirConselho();
        assertTrue(acao.podeExecutar(aluno));
    }

    @Test
    void pedirConselhoNaoDeveExecutarAposLimite() {
        Aluno aluno = new Aluno();
        aluno.incrementarConselhosUsados();
        aluno.incrementarConselhosUsados();
        aluno.incrementarConselhosUsados();
        PedirConselho acao = new PedirConselho();
        assertFalse(acao.podeExecutar(aluno));
    }

    @Test
    void finalizarTurnoDevePoderExecutarComDinheiroSuficiente() {
        assertTrue(new FinalizarTurno().podeExecutar(new Aluno()));
    }

    @Test
    void finalizarTurnoNaoDeveExecutarSemDinheiro() {
        Aluno aluno = new Aluno();
        aluno.gastarDinheiro(200.0);
        assertFalse(new FinalizarTurno().podeExecutar(aluno));
    }

    @Test
    void interagirAnimaisDevePoderExecutarSemInteracaoNoTurno() {
        assertTrue(new InteragirAnimais().podeExecutar(new Aluno()));
    }

    @Test
    void interagirAnimaisNaoDeveExecutarAposUmaInteracao() {
        Aluno aluno = new Aluno();
        aluno.incrementarInteracoesAnimais();
        assertFalse(new InteragirAnimais().podeExecutar(aluno));
    }

    @Test
    void consultarProgressoDevePoderExecutarSempre() {
        assertTrue(new ConsultarProgresso().podeExecutar(new Aluno()));
    }
}