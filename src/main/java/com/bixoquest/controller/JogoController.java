/*******************************************************************************
 Autores: Ramon Santos da Silva, Lucas Willis Rios Bastos
 Componente Curricular: MI - Algoritmos e Programação II
 Fase III concluida em: 30/06/2026
 Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
 trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 ******************************************************************************************/

package com.bixoquest.controller;

import com.bixoquest.model.*;
import com.bixoquest.view.SistemaDeAviso;

/**
 * Mediator concreto que coordena interações entre os componentes do jogo.
 * Implementa o padrão Mediator para reduzir acoplamento entre Aluno, Disciplinas, Eventos e Locais.
 */
public class JogoController implements Mediator {

    private Jogo jogo;
    private Local localAtual;
    private SistemaDeAviso sistemaDeAviso;

    public JogoController() {
        this.jogo = new Jogo();
        this.localAtual = new PontoDeOnibus(jogo);
        this.sistemaDeAviso = new SistemaDeAviso();
    }

    public JogoController(Jogo jogoCarregado) {
        this.jogo = jogoCarregado;
        this.localAtual = new PontoDeOnibus(jogoCarregado);
        this.sistemaDeAviso = new SistemaDeAviso();
    }

    /**
     * Executa uma ação após validação pelo mediator.
     */
    @Override
    public void executarAcao(Acao acao) {
        if (coordenarAcao(acao, jogo.getAluno())) {
            acao.executarAcao(jogo.getAluno(), jogo);
            notificarAcao(acao);
        }
    }

    /**
     * Move o aluno para um novo local após validação.
     */
    @Override
    public void moverPara(Local local) {
        if (local == null) {
            System.err.println("[MEDIATOR] Erro: Local não pode ser null");
            return;
        }

        this.localAtual = local;
        jogo.getAluno().moverPara(local);
        notificarMovimento(local);

        System.out.println("[MEDIATOR] Aluno movido para: " + local.getNome());
    }

    /**
     * Finaliza o turno com coordenação de eventos.
     */
    @Override
    public void finalizarTurno() {
        System.out.println("[MEDIATOR] Finalizando turno " + jogo.getTurnoAtual());
        jogo.finalizarTurno();
        notificarFinalizacaoTurno();
    }

    /**
     * Coordena a execução de uma ação, validando pré-condições.
     */
    @Override
    public boolean coordenarAcao(Acao acao, Aluno aluno) {
        // Validações gerais
        if (acao == null || aluno == null) {
            System.err.println("[MEDIATOR] Erro: Ação ou Aluno inválido");
            return false;
        }

        // Validação específica da ação
        if (!podeExecutar(acao)) {
            System.out.println("[MEDIATOR] Ação não pode ser executada no momento");
            return false;
        }

        // Validação de precondições (ex: energia suficiente)
        if (!acao.podeExecutar(aluno)) {
            System.out.println("[MEDIATOR] Precondições da ação não foram atendidas");
            return false;
        }

        return true;
    }

    /**
     * Verifica se uma ação pode ser executada no contexto atual.
     */
    @Override
    public boolean podeExecutar(Acao acao) {
        // Ações de movimento só podem em certos locais
        if (acao instanceof FinalizarTurno) {
            return localAtual instanceof PontoDeOnibus;
        }

        // Outras ações podem ser executadas em qualquer local
        return true;
    }

    /**
     * Notifica sobre execução de uma ação.
     */
    @Override
    public void notificarAcao(Acao acao) {
        System.out.println("[MEDIATOR] Ação executada: " + acao.getClass().getSimpleName());
    }

    /**
     * Notifica sobre movimento do aluno.
     */
    @Override
    public void notificarMovimento(Local local) {
        System.out.println("[MEDIATOR] Aluno em novo local: " + local.getNome());
    }

    /**
     * Notifica sobre finalização de turno.
     */
    @Override
    public void notificarFinalizacaoTurno() {
        System.out.println("[MEDIATOR] Turno " + (jogo.getTurnoAtual() - 1) + " finalizado");
    }

    // Getters
    public SistemaDeAviso getSistemaDeAviso() { return sistemaDeAviso; }
    public Jogo getJogo() { return jogo; }
    public Aluno getAluno() { return jogo.getAluno(); }
    public Local getLocalAtual() { return localAtual; }
}