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

import com.bixoquest.model.Acao;
import com.bixoquest.model.Aluno;
import com.bixoquest.model.Jogo;

public class JogoController {

    private Jogo jogo;

    public JogoController(Jogo jogo) {
        this.jogo = jogo;
    }public JogoController() {
        this.jogo = new Jogo();
    }

    public void executarAcao(Acao acao) {
        // garante que a ação só é executada se as condições forem atendidas
        if (acao.podeExecutar(jogo.getAluno())) {
            acao.executarAcao(jogo.getAluno(), jogo);
        }
    }

    public void finalizarTurno() { jogo.finalizarTurno(); }
    public Jogo getJogo() { return jogo; }
    public Aluno getAluno() { return jogo.getAluno(); }
}