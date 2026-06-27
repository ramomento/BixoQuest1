package com.bixoquest.view;

import com.bixoquest.controller.JogoController;
import com.bixoquest.model.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.List;

public class TelaPrincipalView {

    private int opcaoSelecionada = 0;
    private ImageView cursor;
    private Scene scene;
    private Pane pane;
    private JogoController controller;
    private PopUpView popUpAtual;
    private ItemCantina itemSelecionadoCompra;

    private enum EstadoMenu {
        PRINCIPAL, LOCOMOVER, ACOES, POP_UP
    }
    private EstadoMenu estadoMenu = EstadoMenu.PRINCIPAL;

    private enum TipoPopUp {
        CONSELHO, PROGRESSO, PROVA, CONFIRMACAO_COMPRA, INTERAGIR_ANIMAIS
    }
    private TipoPopUp tipoPopUpAtual;

    private String[] opcoesMenuPrincipal = {"Locomover", "Executar Ação"};
    private String[] opcoesLocais = {"Sala de Aula", "Laboratório", "Cantina", "Colegiado", "Ponto de Ônibus"};
    private String[] opcoesAcoes = {};
    private String[] opcoesAtuais = opcoesMenuPrincipal;

    public TelaPrincipalView(JogoController controller) {
        this.controller = controller;
    }

    public Scene criarCena() {
        pane = new Pane();
        pane.setPrefSize(800, 600);

        // sprite do local atual
        ImageView local = carregarImagem(obterCaminhoSprite(), 0, 0, 800, 360);
        pane.getChildren().add(local);

        // painel inferior
        ImageView painel = carregarImagem("/images/ui/tela_principal2.png", -15, 340, 815, 237);
        pane.getChildren().add(painel);

        // painel esquerdo (informações ou avisos)
        atualizarPainelEsquerdo();

        // cursor para o menu
        cursor = carregarImagem("/images/ui/cursor.png", 550, 375, 16, 16);
        pane.getChildren().add(cursor);

        // renderizar menu
        atualizarMenu();

        scene = new Scene(pane);
        scene.setFill(Color.BLACK);

        configurarTeclado();

        pane.setFocusTraversable(true);
        pane.requestFocus();

        // Verificar eventos ao entrar
        verificarEventos();

        return scene;
    }

    private void verificarEventos() {
        List<Evento> eventos = controller.getJogo().getSistemaDeEventos().getEventosFixos();

        for (Evento evento : eventos) {
            if (evento instanceof EventoProva) {
                EventoProva prova = (EventoProva) evento;

                // Se a prova está ativa E é o turno correto, mostra o pop-up UMA VEZ
                if (prova.isAtivo() && prova.getTurnoAplicacao() == controller.getJogo().getTurnoAtual()) {
                    String disciplina = prova.getDisciplina().getNome();
                    exibirPopUpProva(disciplina);
                    prova.setAtivo(false);  // ← Marca como já visualizada
                    break;
                }
            }
        }
    }

    private String obterCaminhoSprite() {
        String localNome = controller.getLocalAtual().getNome();

        switch (localNome) {
            case "Sala de Aula":
                return "/images/locais/sala_de_aula.png";
            case "Laboratório":
                return "/images/locais/laboratorio.png";
            case "Cantina":
                return "/images/locais/cantina.png";
            case "Colegiado":
                return "/images/locais/colegiado.png";
            case "Ponto de Ônibus":
                return "/images/locais/ponto_de_onibus.png";
            default:
                return "/images/locais/ponto_de_onibus.png";
        }
    }

    private void atualizarPainelEsquerdo() {
        pane.getChildren().removeIf(node ->
                node instanceof Text && ((Text) node).getX() < 560 && ((Text) node).getY() >= 415
        );

        renderizarInfoAluno();

        SistemaDeAviso aviso = controller.getSistemaDeAviso();

        if (aviso.getTipoAviso() == SistemaDeAviso.TipoAviso.AVISOS &&
                !aviso.getTextoAviso().isEmpty()) {
            renderizarAvisoLado(aviso.getTextoAviso());
        } else if (aviso.getTipoAviso() == SistemaDeAviso.TipoAviso.TEXTO &&
                !aviso.getTextoAviso().isEmpty()) {
            renderizarMensagemAbaixo(aviso.getTextoAviso());
        }
    }

    private void renderizarInfoAluno() {
        Aluno aluno = controller.getAluno();

        Text energia = new Text("Energia: " + aluno.getEnergia());
        energia.setFont(GameFont.small());
        energia.setX(30);
        energia.setY(415);
        energia.setFill(Color.WHITE);
        pane.getChildren().add(energia);

        Text saude = new Text("Saúde: " + aluno.getSaude().toString());
        saude.setFont(GameFont.small());
        saude.setX(30);
        saude.setY(435);
        saude.setFill(Color.WHITE);
        pane.getChildren().add(saude);

        Text motivacao = new Text("Motivação: " + aluno.getMotivacao().toString());
        motivacao.setFont(GameFont.small());
        motivacao.setX(30);
        motivacao.setY(455);
        motivacao.setFill(Color.WHITE);
        pane.getChildren().add(motivacao);

        Text dinheiro = new Text("Dinheiro: R$ " + String.format("%.1f", aluno.getDinheiro()));
        dinheiro.setFont(GameFont.small());
        dinheiro.setX(30);
        dinheiro.setY(475);
        dinheiro.setFill(Color.WHITE);
        pane.getChildren().add(dinheiro);

        Text semestre = new Text("Semestre " + controller.getJogo().getSemestreAtual().getNumero() +
                " - Turno " + controller.getJogo().getSemestreAtual().getTurnoAtual());
        semestre.setFont(GameFont.small());
        semestre.setX(30);
        semestre.setY(495);
        semestre.setFill(Color.WHITE);
        pane.getChildren().add(semestre);
    }

    private void renderizarMensagemAbaixo(String mensagem) {
        Text msg = new Text(mensagem);
        msg.setFont(GameFont.small());
        msg.setX(30);
        msg.setY(520);
        msg.setFill(Color.web("#FFD700"));
        msg.setWrappingWidth(500);
        pane.getChildren().add(msg);
    }

    private void renderizarAvisoLado(String aviso) {
        Text avisoText = new Text(aviso);
        avisoText.setFont(GameFont.small());
        avisoText.setX(320);
        avisoText.setY(440);
        avisoText.setFill(Color.web("#FF6B6B"));
        avisoText.setWrappingWidth(220);
        pane.getChildren().add(avisoText);
    }

    private void atualizarMenu() {
        pane.getChildren().removeIf(node ->
                node instanceof Text && ((Text) node).getX() >= 570 && ((Text) node).getX() <= 600
        );

        switch (estadoMenu) {
            case PRINCIPAL:
                opcoesAtuais = opcoesMenuPrincipal;
                break;
            case LOCOMOVER:
                opcoesAtuais = opcoesLocais;
                break;
            case ACOES:
                opcoesAtuais = gerarOpcoesAcoes();
                break;
            case POP_UP:
                return;
        }

        for (int i = 0; i < opcoesAtuais.length; i++) {
            Text opcao = new Text(opcoesAtuais[i]);
            opcao.setFont(GameFont.small());
            opcao.setX(570);
            opcao.setY(390 + (i * 30));

            if (i == opcaoSelecionada) {
                opcao.setFill(Color.web("#FFD700"));
            } else {
                opcao.setFill(Color.WHITE);
            }

            pane.getChildren().add(opcao);
        }
    }

    private String[] gerarOpcoesAcoes() {
        List<Acao> acoes = controller.getLocalAtual().getAcoesDisponiveis();
        String[] opcoes = new String[acoes.size() + 1];

        for (int i = 0; i < acoes.size(); i++) {
            opcoes[i] = obterNomeAcao(acoes.get(i));
        }
        opcoes[acoes.size()] = "Voltar";

        return opcoes;
    }

    private String obterNomeAcao(Acao acao) {
        if (acao instanceof CursarDisciplina) return "Cursar Disciplina";
        if (acao instanceof PedirConselho) return "Pedir Conselho";
        if (acao instanceof ConsultarProgresso) return "Consultar Progresso";
        if (acao instanceof Comer) {
            Comer comer = (Comer) acao;
            return "Comprar " + comer.getItem().getNome();
        }
        if (acao instanceof InteragirAnimais) return "Interagir com Animais";
        if (acao instanceof FinalizarTurno) return "Finalizar Turno";
        return "Ação";
    }

    private void configurarTeclado() {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();

            if (estadoMenu == EstadoMenu.POP_UP) {
                if (tipoPopUpAtual == TipoPopUp.CONFIRMACAO_COMPRA) {
                    if (code == KeyCode.LEFT) {
                        popUpAtual.opcaoAnterior();
                    } else if (code == KeyCode.RIGHT) {
                        popUpAtual.proximaOpcao();
                    } else if (code == KeyCode.ENTER) {
                        processarRespostaCompra();
                    } else if (code == KeyCode.ESCAPE) {
                        fecharPopUp();
                    }
                } else if (tipoPopUpAtual == TipoPopUp.INTERAGIR_ANIMAIS) {
                    if (code == KeyCode.LEFT) {
                        popUpAtual.opcaoAnterior();
                    } else if (code == KeyCode.RIGHT) {
                        popUpAtual.proximaOpcao();
                    } else if (code == KeyCode.ENTER) {
                        processarInteracaoAnimal();
                    } else if (code == KeyCode.ESCAPE) {
                        fecharPopUp();
                    }
                } else {
                    if (code == KeyCode.LEFT) {
                        popUpAtual.opcaoAnterior();
                    } else if (code == KeyCode.RIGHT) {
                        popUpAtual.proximaOpcao();
                    } else if (code == KeyCode.ENTER) {
                        fecharPopUp();
                    } else if (code == KeyCode.ESCAPE) {
                        fecharPopUp();
                    }
                }
                event.consume();
                return;
            }

            if (code == KeyCode.UP) {
                if (opcaoSelecionada > 0) {
                    opcaoSelecionada--;
                    atualizarMenu();
                    atualizarCursor();
                }
            } else if (code == KeyCode.DOWN) {
                if (opcaoSelecionada < opcoesAtuais.length - 1) {
                    opcaoSelecionada++;
                    atualizarMenu();
                    atualizarCursor();
                }
            } else if (code == KeyCode.ENTER) {
                selecionarOpcao();
            } else if (code == KeyCode.ESCAPE) {
                if (estadoMenu != EstadoMenu.PRINCIPAL) {
                    estadoMenu = EstadoMenu.PRINCIPAL;
                    opcaoSelecionada = 0;
                    controller.getSistemaDeAviso().exibirMensagem("");
                    atualizarPainelEsquerdo();
                    atualizarMenu();
                    atualizarCursor();
                } else {
                    App.mostrarMenuInicial();
                }
            }

            event.consume();
        });
    }

    private void atualizarCursor() {
        double posY = 375 + (opcaoSelecionada * 30);
        cursor.setY(posY);
    }

    private void selecionarOpcao() {
        switch (estadoMenu) {
            case PRINCIPAL:
                selecionarOpcaoPrincipal();
                break;
            case LOCOMOVER:
                selecionarLocal();
                break;
            case ACOES:
                selecionarAcao();
                break;
        }
    }

    private void selecionarOpcaoPrincipal() {
        if (opcaoSelecionada == 0) {
            estadoMenu = EstadoMenu.LOCOMOVER;
            opcaoSelecionada = 0;
            atualizarMenu();
            atualizarCursor();
        } else if (opcaoSelecionada == 1) {
            estadoMenu = EstadoMenu.ACOES;
            opcaoSelecionada = 0;
            atualizarMenu();
            atualizarCursor();
        }
    }

    private void selecionarLocal() {
        Local[] locais = {
                new SalaDeAula(controller.getJogo()),
                new Laboratorio(controller.getJogo()),
                new Cantina(controller.getJogo()),
                new Colegiado(controller.getJogo()),
                new PontoDeOnibus(controller.getJogo())
        };

        controller.moverPara(locais[opcaoSelecionada]);
        estadoMenu = EstadoMenu.PRINCIPAL;
        opcaoSelecionada = 0;
        controller.getSistemaDeAviso().exibirMensagem("");

        App.mostrarTelaPrincipal();
    }

    private void selecionarAcao() {
        List<Acao> acoes = controller.getLocalAtual().getAcoesDisponiveis();

        if (opcaoSelecionada >= acoes.size()) {
            estadoMenu = EstadoMenu.PRINCIPAL;
            opcaoSelecionada = 0;
            controller.getSistemaDeAviso().exibirMensagem("");
            atualizarMenu();
            atualizarCursor();
            return;
        }

        Acao acao = acoes.get(opcaoSelecionada);

        // Verificar ações especiais
        if (acao instanceof PedirConselho) {
            exibirPopUpConselho();
            return;
        }

        if (acao instanceof ConsultarProgresso) {
            exibirPopUpProgresso();
            return;
        }

        if (acao instanceof Comer) {
            Comer comer = (Comer) acao;
            exibirPopUpConfirmacaoCompra(comer.getItem());
            return;
        }

        if (acao instanceof InteragirAnimais) {
            exibirPopUpInteragirAnimais();
            return;
        }

        // Executar ação normal
        controller.executarAcao(acao);
        String nomeAcao = obterNomeAcao(acao);
        controller.getSistemaDeAviso().exibirMensagem("Executou: " + nomeAcao);

        estadoMenu = EstadoMenu.PRINCIPAL;
        opcaoSelecionada = 0;
        App.mostrarTelaPrincipal();
    }

    private void exibirPopUpConfirmacaoCompra(ItemCantina item) {
        itemSelecionadoCompra = item;

        popUpAtual = new PopUpView();
        popUpAtual.setTitulo("Confirmar Compra");
        popUpAtual.setConteudo(item.getNome() + "\n\nPreço: R$ " +
                String.format("%.1f", item.getPreco()) +
                "\n\nDeseja comprar?");
        popUpAtual.setOpcoes(new String[]{"Sim", "Não"});

        pane.getChildren().add(popUpAtual.criarPopUp());
        estadoMenu = EstadoMenu.POP_UP;
        tipoPopUpAtual = TipoPopUp.CONFIRMACAO_COMPRA;
    }

    private void processarRespostaCompra() {
        int resposta = popUpAtual.getOpcaoSelecionada();
        Aluno aluno = controller.getAluno();

        if (resposta == 0) { // Sim
            if (aluno.temDinheiroSuficiente(itemSelecionadoCompra.getPreco())) {
                Comer comer = new Comer(itemSelecionadoCompra);
                controller.executarAcao(comer);
                controller.getSistemaDeAviso().exibirMensagem("Comprou " + itemSelecionadoCompra.getNome() + "!");
            } else {
                controller.getSistemaDeAviso().exibirMensagem("Dinheiro insuficiente!");
            }
        }

        fecharPopUp();
    }

    private void exibirPopUpInteragirAnimais() {
        popUpAtual = new PopUpView();
        popUpAtual.setTitulo("Interagir com Animais");
        popUpAtual.setConteudo("Escolha um animal para interagir:\n\nEsquerda/Direita para navegar");
        popUpAtual.setOpcoes(new String[]{"Gato", "Cachorro"});

        pane.getChildren().add(popUpAtual.criarPopUp());
        estadoMenu = EstadoMenu.POP_UP;
        tipoPopUpAtual = TipoPopUp.INTERAGIR_ANIMAIS;
    }

    private void processarInteracaoAnimal() {
        int escolha = popUpAtual.getOpcaoSelecionada();
        InteragirAnimais interagir = new InteragirAnimais();

        if (interagir.podeExecutar(controller.getAluno())) {
            controller.executarAcao(interagir);
            String animal = (escolha == 0) ? "Gato" : "Cachorro";
            controller.getSistemaDeAviso().exibirMensagem("Você brincou com o " + animal + "!");
        } else {
            controller.getSistemaDeAviso().exibirMensagem("Você já interagiu com os animais hoje.");
        }

        fecharPopUp();
    }

    private void exibirPopUpConselho() {
        popUpAtual = new PopUpView();
        popUpAtual.setTitulo("Conselho");
        popUpAtual.setConteudo("Gerencie bem seu dinheiro, as despesas aparecem quando menos se espera!");
        popUpAtual.setOpcoes(new String[]{"OK"});

        pane.getChildren().add(popUpAtual.criarPopUp());
        estadoMenu = EstadoMenu.POP_UP;
        tipoPopUpAtual = TipoPopUp.CONSELHO;
    }

    private void exibirPopUpProgresso() {
        popUpAtual = new PopUpView();
        popUpAtual.setTitulo("Seu Progresso");
        popUpAtual.setConteudo("Sala de Aula: Cursando\nLaboratório: Disponível\nCantina: Desbloqueada");
        popUpAtual.setOpcoes(new String[]{"OK"});

        pane.getChildren().add(popUpAtual.criarPopUp());
        estadoMenu = EstadoMenu.POP_UP;
        tipoPopUpAtual = TipoPopUp.PROGRESSO;
    }

    private void exibirPopUpProva(String disciplina) {
        popUpAtual = new PopUpView();
        popUpAtual.setTitulo("⚠️ Prova Disponível");
        popUpAtual.setConteudo(disciplina + "\n\nVocê pode fazer a prova neste turno.");
        popUpAtual.setOpcoes(new String[]{"OK"});  // ← Apenas OK, sem opções

        pane.getChildren().add(popUpAtual.criarPopUp());
        estadoMenu = EstadoMenu.POP_UP;
        tipoPopUpAtual = TipoPopUp.PROVA;
    }

    private void fecharPopUp() {
        if (popUpAtual != null) {
            pane.getChildren().remove(popUpAtual.getPane());
        }

        estadoMenu = EstadoMenu.PRINCIPAL;
        opcaoSelecionada = 0;
        App.mostrarTelaPrincipal();
    }

    private ImageView carregarImagem(String caminho, double x, double y, double largura, double altura) {
        Image imagem = new Image(getClass().getResourceAsStream(caminho));
        ImageView view = new ImageView(imagem);
        view.setX(x);
        view.setY(y);
        view.setFitWidth(largura);
        view.setFitHeight(altura);
        return view;
    }
}