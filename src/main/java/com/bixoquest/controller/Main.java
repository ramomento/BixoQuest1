// Main.java
package com.bixoquest.controller;

import com.bixoquest.model.Jogo;
import com.bixoquest.persistence.GerenciadorDeSaves;
import com.bixoquest.model.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Local> LOCAIS = new ArrayList<>();

    public static void main(String[] args) {
        exibirMenuInicial();
    }

    private static void exibirMenuInicial() {
        while (true) {
            System.out.println("\n=== BixoQuest ===");
            System.out.println("1. Nova partida");
            System.out.println("2. Carregar partida");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            int opcao = lerInteiro();
            switch (opcao) {
                case 1 -> iniciarNovaPartida();
                case 2 -> carregarPartida();
                case 0 -> { System.out.println("Até logo!"); return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void iniciarNovaPartida() {
        int slot = selecionarSlot(false);
        if (slot == 0) return;

        JogoController controller = new JogoController();
        System.out.println("Nova partida iniciada no slot " + slot + "!");
        GerenciadorDeSaves.salvar(controller.getJogo(), slot);
        iniciarJogo(controller, slot);
    }

    private static void carregarPartida() {
        int slot = selecionarSlot(true);
        if (slot == 0) return;

        Jogo jogo = GerenciadorDeSaves.carregar(slot);
        if (jogo == null) {
            System.out.println("Erro ao carregar o slot " + slot + ".");
            return;
        }

        JogoController controller = new JogoController(jogo);
        System.out.println("Partida carregada!");
        iniciarJogo(controller, slot);
    }

    private static int selecionarSlot(boolean apenasOcupados) {
        System.out.println("\n=== Slots de Save ===");
        for (int i = 1; i <= 3; i++) {
            boolean disponivel = GerenciadorDeSaves.slotDisponivel(i);
            System.out.println(i + ". Slot " + i + " — " + (disponivel ? "Vazio" : "Ocupado"));
        }
        System.out.println("0. Voltar");
        System.out.print("Escolha: ");

        int slot = lerInteiro();
        if (slot == 0) return 0;
        if (slot < 1 || slot > 3) { System.out.println("Slot inválido."); return 0; }

        boolean disponivel = GerenciadorDeSaves.slotDisponivel(slot);
        if (apenasOcupados && disponivel) {
            System.out.println("Slot vazio — sem partida para carregar.");
            return 0;
        }

        return slot;
    }

    private static int lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }



    private static void iniciarJogo(JogoController controller, int slot) {
        Jogo jogo = controller.getJogo();
        inicializarLocais(jogo);

        // posiciona o aluno no ponto de ônibus ao iniciar
        jogo.getAluno().moverPara(LOCAIS.get(0));

        while (true) {
            exibirStatusAluno(jogo);

            if (verificarFimDeJogo(jogo)) return;

            System.out.println("\nO que deseja fazer?");
            System.out.println("1. Mover para outro local");
            System.out.println("2. Realizar ação no local atual");
            System.out.println("0. Voltar ao menu");
            System.out.print("Escolha: ");

            switch (lerInteiro()) {
                case 1 -> moverAluno(jogo);
                case 2 -> {
                    int turnoAntes = controller.getJogo().getTurnoAtual();
                    executarAcaoNoLocal(controller);
                    if (controller.getJogo().getTurnoAtual() > turnoAntes) {
                        exibirFeedbackEvento(controller.getJogo());
                        GerenciadorDeSaves.salvar(jogo, slot);
                        System.out.println("Progresso salvo automaticamente.");
                    }
                }
                case 0 -> { return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void inicializarLocais(Jogo jogo) {
        LOCAIS.clear();
        LOCAIS.add(new PontoDeOnibus(jogo));
        LOCAIS.add(new SalaDeAula(jogo));
        LOCAIS.add(new Laboratorio(jogo));
        LOCAIS.add(new Cantina(jogo));
        LOCAIS.add(new Colegiado(jogo));
    }

    private static void exibirStatusAluno(Jogo jogo) {
        Aluno aluno = jogo.getAluno();
        System.out.println("\n=== Turno " + jogo.getTurnoAtual() + " | Semestre " + jogo.getSemestreAtual().getNumero() + " ===");
        System.out.println("Local: " + aluno.getLocalAtual().getNome());
        System.out.println("Energia: " + aluno.getEnergia() + " | Saúde: " + aluno.getSaude() + " | Motivação: " + aluno.getMotivacao());
        System.out.println("Dinheiro: R$" + aluno.getDinheiro() + " | Conhecimento T: " + aluno.getConhecimentoTeorico() + " P: " + aluno.getConhecimentoPratico());
    }

    private static void moverAluno(Jogo jogo) {
        if (jogo.isCantinaOcupada()) {
            System.out.println("A cantina está com fila enorme — não é possível ir até lá agora!");
        }

        System.out.println("\nEscolha o local:");
        for (int i = 0; i < LOCAIS.size(); i++) {
            System.out.println((i + 1) + ". " + LOCAIS.get(i).getNome());
        }
        System.out.println("0. Cancelar");
        System.out.print("Escolha: ");

        int opcao = lerInteiro();
        if (opcao == 0) return;
        if (opcao < 1 || opcao > LOCAIS.size()) { System.out.println("Opção inválida."); return; }

        Local destino = LOCAIS.get(opcao - 1);

        if (destino instanceof Cantina && jogo.isCantinaOcupada()) {
            System.out.println("Não foi possível entrar na cantina.");
            return;
        }

        jogo.getAluno().moverPara(destino);
        System.out.println("Você foi para: " + destino.getNome());
    }

    private static boolean verificarFimDeJogo(Jogo jogo) {
        Aluno aluno = jogo.getAluno();

        long aprovadas = jogo.getDisciplinasDisponiveis().stream()
                .filter(d -> d.getEstado() == com.bixoquest.enums.EstadoDisciplina.APROVADO)
                .count();

        if (aprovadas == 10) {
            System.out.println("\n=== PARABÉNS! ===");
            System.out.println("Você concluiu todas as disciplinas e se formou!");
            return true;
        }

        return false;
    }

    private static void executarAcaoNoLocal(JogoController controller) {
        Aluno aluno = controller.getAluno();
        Local localAtual = aluno.getLocalAtual();
        List<Acao> acoes = localAtual.getAcoesDisponiveis();

        System.out.println("\n=== Ações disponíveis em " + localAtual.getNome() + " ===");
        for (int i = 0; i < acoes.size(); i++) {
            Acao acao = acoes.get(i);
            String disponivel = acao.podeExecutar(aluno) ? "" : " [indisponível]";
            System.out.println((i + 1) + ". " + acao.getClass().getSimpleName() + disponivel);
        }
        System.out.println("0. Cancelar");
        System.out.print("Escolha: ");

        int opcao = lerInteiro();
        if (opcao == 0) return;
        if (opcao < 1 || opcao > acoes.size()) { System.out.println("Opção inválida."); return; }

        Acao acaoEscolhida = acoes.get(opcao - 1);
        if (!acaoEscolhida.podeExecutar(aluno)) {
            System.out.println("Você não pode realizar essa ação agora.");
            return;
        }

        controller.executarAcao(acaoEscolhida);
        System.out.println("Ação realizada com sucesso!");
    }

    private static void exibirFeedbackEvento(Jogo jogo) {
        System.out.println("\n=== Fim de Turno ===");
        System.out.println("Turno " + (jogo.getTurnoAtual() - 1) + " encerrado.");

        if (jogo.getSemestreAtual().getTurnoAtual() == 1) {
            System.out.println("\n=== Fim de Semestre ===");
            System.out.println("Resultado das disciplinas:");
            for (Disciplina d : jogo.getSemestreAtual().getDisciplinas()) {
                System.out.println(d.getNome() + " | " + d.getEstado() + " | Média: " + String.format("%.1f", d.calcularMedia()));
            }
            System.out.println("\nSemestre " + jogo.getSemestreAtual().getNumero() + " iniciado!");
        }

        if (jogo.getProvaAtiva() != null) {
            System.out.println("\n⚠ Há uma prova disponível em " +
                    (jogo.getProvaAtiva().getDisciplina().getTipo() == com.bixoquest.enums.TipoDisciplina.SOFTWARE
                            ? "Sala de Aula" : "Laboratório") + "!");
        }
    }
}