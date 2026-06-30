/*******************************************************************************
 Autores: Ramon Santos da Silva, Lucas Willis Rios Bastos
 Componente Curricular: MI - Algoritmos e Programação II
 Fase II concluida em: 22/05/2026
 Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
 trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 ******************************************************************************************/

package com.bixoquest.controller;

import com.bixoquest.model.*;
import com.bixoquest.view.SistemaDeAviso;

public class JogoController {

    private Jogo jogo;
    private Local localAtual;
    private SistemaDeAviso sistemaDeAviso;


    public JogoController() {
        this.jogo = new Jogo();
        this.localAtual = new PontoDeOnibus(jogo);
        this.sistemaDeAviso = new SistemaDeAviso();  // ADICIONE
    }

    public JogoController(Jogo jogoCarregado) {
        this.jogo = jogoCarregado;
        this.localAtual = new PontoDeOnibus(jogoCarregado);
        this.sistemaDeAviso = new SistemaDeAviso();
    }

    public void executarAcao(Acao acao) {
        if (acao.podeExecutar(jogo.getAluno())) {
            acao.executarAcao(jogo.getAluno(), jogo);
        }
    }

    public void moverPara(Local local) {
        this.localAtual = local;
        jogo.getAluno().moverPara(local);
    }

    public void finalizarTurno() {
        jogo.finalizarTurno();
    }

    public SistemaDeAviso getSistemaDeAviso() { return sistemaDeAviso; }

    public Jogo getJogo() { return jogo; }
    public Aluno getAluno() { return jogo.getAluno(); }
    public Local getLocalAtual() { return localAtual; }
}