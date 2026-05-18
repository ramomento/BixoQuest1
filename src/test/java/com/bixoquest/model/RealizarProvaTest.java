package com.bixoquest.model;

import com.bixoquest.enums.EstadoDisciplina;
import com.bixoquest.enums.EstadoMotivacao;
import com.bixoquest.enums.EstadoSaude;
import com.bixoquest.enums.TipoDisciplina;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RealizarProvaTest {

    private Aluno aluno;
    private Disciplina disciplina;
    private EventoProva eventoProva;
    private RealizarProva acao;

    @BeforeEach
    void setUp() {
        aluno = new Aluno();
        disciplina = new Disciplina("Software 1", TipoDisciplina.SOFTWARE, null);
        disciplina.setEstado(EstadoDisciplina.CURSANDO);

        // prova teórica: peso 70 teórico, 30 prático, conhecimento esperado 100
        eventoProva = new EventoProva(disciplina, 1.0, 70, 30, 1, 100);
        acao = new RealizarProva(eventoProva);
    }

    // --- podeExecutar ---

    @Test
    void devePoderExecutarQuandoProvaNaoRealizadaEDisciplinaCursando() {
        assertTrue(acao.podeExecutar(aluno));
    }

    @Test
    void naoDevePoderExecutarQuandoProvaJaRealizada() {
        eventoProva.setRealizada(true);
        assertFalse(acao.podeExecutar(aluno));
    }

    @Test
    void naoDevePoderExecutarQuandoDisciplinaNaoCursando() {
        disciplina.setEstado(EstadoDisciplina.DISPONIVEL);
        assertFalse(acao.podeExecutar(aluno));
    }

    // --- executarAcao ---

    @Test
    void executarAcaoDeveAdicionarNotaNaDisciplina() {
        acao.executarAcao(aluno, new Jogo());
        assertEquals(1, disciplina.getNotas().size());
    }

    @Test
    void executarAcaoDeveMarcarProvaComoRealizada() {
        acao.executarAcao(aluno, new Jogo());
        assertTrue(eventoProva.isRealizada());
    }

    // --- limites da nota ---

    @Test
    void notaDeveSempreEstarEntre0e10() {
        // executa múltiplas vezes para cobrir os diferentes ramos aleatórios
        aluno.aumentarConhecimentoTeorico(70);
        aluno.aumentarConhecimentoPratico(30);

        for (int i = 0; i < 200; i++) {
            double nota = acao.calcularNota(aluno);
            assertTrue(nota >= 0 && nota <= 10,
                    "Nota fora do intervalo: " + nota);
        }
    }

    @Test
    void notaDeveSerZeroComConhecimentoZero() {
        // conhecimento 0 → nota base 0 → qualquer multiplicador sobre 0 resulta em 0
        assertEquals(0.0, acao.calcularNota(aluno));
    }

    // --- cálculo base ---

    @Test
    void notaBaseDeveRefletirPesoTeorico() {
        // apenas conhecimento teórico, peso 70 — sem aleatoriedade pois aluno
        // está em condição ideal (SAUDAVEL + ALTA + energia 10 = 0% chance negativa)
        // e chance positiva é baixa — rodamos várias vezes e verificamos tendência
        aluno.aumentarConhecimentoTeorico(100); // 100% do esperado via teórico

        int notasAcimaDe6 = 0;
        for (int i = 0; i < 100; i++) {
            // recria prova para evitar limite de 3 notas na disciplina
            Disciplina d = new Disciplina("Software 1", TipoDisciplina.SOFTWARE, null);
            d.setEstado(EstadoDisciplina.CURSANDO);
            EventoProva ep = new EventoProva(d, 1.0, 70, 30, 1, 100);
            RealizarProva a = new RealizarProva(ep);
            double nota = a.calcularNota(aluno);
            if (nota >= 6.0) notasAcimaDe6++;
        }
        // com condições ideais a maioria das notas deve ser alta
        assertTrue(notasAcimaDe6 > 70,
                "Esperado mais de 70 notas >= 6 em 100 tentativas, obtido: " + notasAcimaDe6);
    }

    @Test
    void conhecimentoAcimaDoEsperadoNaoDeveUltrapassarNota10() {
        // conhecimento muito acima do esperado — percentual é fixado em 1.0
        aluno.aumentarConhecimentoTeorico(500);
        aluno.aumentarConhecimentoPratico(500);

        for (int i = 0; i < 100; i++) {
            double nota = acao.calcularNota(aluno);
            assertTrue(nota <= 10, "Nota ultrapassou 10: " + nota);
        }
    }
}