package com.bixoquest.persistence;

import com.bixoquest.enums.*;
import com.bixoquest.model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GerenciadorDeSavesTest {

    private static final int SLOT = 1;
    private static final String CAMINHO_SAVE = "saves/save" + SLOT + ".txt";

    private Jogo jogo;

    @BeforeEach
    void setUp() {
        jogo = new Jogo();
    }

    @AfterEach
    void tearDown() {
        File arquivo = new File(CAMINHO_SAVE);
        if (arquivo.exists()) arquivo.delete();
    }

    // --- slotDisponivel ---

    @Test
    void slotDeveEstarDisponivelQuandoArquivoNaoExiste() {
        assertTrue(GerenciadorDeSaves.slotDisponivel(SLOT));
    }

    @Test
    void slotDeveEstarOcupadoAposSalvar() {
        GerenciadorDeSaves.salvar(jogo, SLOT);
        assertFalse(GerenciadorDeSaves.slotDisponivel(SLOT));
    }

    // --- salvar ---

    @Test
    void salvarDevecriarArquivo() {
        GerenciadorDeSaves.salvar(jogo, SLOT);
        assertTrue(new File(CAMINHO_SAVE).exists());
    }

    @Test
    void salvarDeveGerarArquivoNaoVazio() {
        GerenciadorDeSaves.salvar(jogo, SLOT);
        assertTrue(new File(CAMINHO_SAVE).length() > 0);
    }

    // --- carregar: atributos do Aluno ---

    @Test
    void carregarDeveRestaurarAtributosDoAluno() {
        Aluno aluno = jogo.getAluno();
        aluno.reduzirEnergia(3);
        aluno.alterarSaude(EstadoSaude.CANSADO);
        aluno.alterarMotivacao(EstadoMotivacao.BAIXA);
        aluno.gastarDinheiro(50.0);
        aluno.aumentarConhecimentoTeorico(25);
        aluno.aumentarConhecimentoPratico(25);

        GerenciadorDeSaves.salvar(jogo, SLOT);
        Jogo jogoCarregado = GerenciadorDeSaves.carregar(SLOT);
        Aluno alunoCarregado = jogoCarregado.getAluno();

        assertAll(
                () -> assertEquals(aluno.getEnergia(), alunoCarregado.getEnergia()),
                () -> assertEquals(aluno.getSaude(), alunoCarregado.getSaude()),
                () -> assertEquals(aluno.getMotivacao(), alunoCarregado.getMotivacao()),
                () -> assertEquals(aluno.getDinheiro(), alunoCarregado.getDinheiro()),
                () -> assertEquals(aluno.getConhecimentoTeorico(), alunoCarregado.getConhecimentoTeorico()),
                () -> assertEquals(aluno.getConhecimentoPratico(), alunoCarregado.getConhecimentoPratico())
        );
    }

    // --- carregar: disciplinas ---

    @Test
    void carregarDeveRestaurarEstadoDasDisciplinas() {
        // adiciona notas nas disciplinas cursando para criar um estado não trivial
        for (Disciplina d : jogo.getSemestreAtual().getDisciplinas()) {
            d.adicionarNota(8.0);
            d.adicionarNota(7.0);
            d.adicionarNota(9.0);
        }

        GerenciadorDeSaves.salvar(jogo, SLOT);
        Jogo jogoCarregado = GerenciadorDeSaves.carregar(SLOT);

        List<Disciplina> original = jogo.getDisciplinasDisponiveis();
        List<Disciplina> restaurada = jogoCarregado.getDisciplinasDisponiveis();

        for (int i = 0; i < original.size(); i++) {
            final int indice = i;
            assertAll(
                    () -> assertEquals(original.get(indice).getEstado(),
                            restaurada.get(indice).getEstado()),
                    () -> assertEquals(original.get(indice).getNotas(),
                            restaurada.get(indice).getNotas())
            );
        }
    }

    // --- carregar: jogo e semestre ---

    @Test
    void carregarDeveRestaurarEstadoDoJogoESemestre() {
        jogo.finalizarTurno();
        jogo.finalizarTurno();

        GerenciadorDeSaves.salvar(jogo, SLOT);
        Jogo jogoCarregado = GerenciadorDeSaves.carregar(SLOT);

        assertAll(
                () -> assertEquals(jogo.getTurnoAtual(), jogoCarregado.getTurnoAtual()),
                () -> assertEquals(jogo.getSemestreAtual().getNumero(),
                        jogoCarregado.getSemestreAtual().getNumero()),
                () -> assertEquals(jogo.getSemestreAtual().getTurnoAtual(),
                        jogoCarregado.getSemestreAtual().getTurnoAtual())
        );
    }

    // --- carregar: slot inexistente ---

    @Test
    void carregarSlotInexistenteDeveRetornarNull() {
        assertNull(GerenciadorDeSaves.carregar(SLOT));
    }
}