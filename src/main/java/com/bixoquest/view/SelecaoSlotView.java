package com.bixoquest.view;

import com.bixoquest.controller.JogoController;
import com.bixoquest.model.Jogo;
import com.bixoquest.persistence.GerenciadorDeSaves;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SelecaoSlotView {

    private int slotSelecionado = 0; // 0, 1 ou 2
    private ImageView cursor;
    private Scene scene;

    public Scene criarCena() {
        Pane pane = new Pane();
        pane.setPrefSize(800, 600);

        // fundo com os 3 slots
        ImageView fundo = carregarImagem("/images/ui/selecao_background.png", 0, 0, 800, 560);
        pane.getChildren().add(fundo);

        // cursor que indica qual slot está selecionado
        cursor = carregarImagem("/images/ui/cursor.png", 100, 140, 30, 30);
        pane.getChildren().add(cursor);

        scene = new Scene(pane);
        scene.setFill(Color.BLACK);

        // configura o listener de teclado
        configurarTeclado();

        pane.setFocusTraversable(true);
        pane.requestFocus();

        return scene;
    }

    private void configurarTeclado() {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();

            if (code == KeyCode.UP) {
                if (slotSelecionado > 0) {
                    slotSelecionado--;
                    atualizarCursor();
                }
            } else if (code == KeyCode.DOWN) {
                if (slotSelecionado < 2) {
                    slotSelecionado++;
                    atualizarCursor();
                }
            } else if (code == KeyCode.ENTER) {
                selecionarSlot();
            } else if (code == KeyCode.ESCAPE) {
                App.mostrarMenuInicial();
            }

            event.consume();
        });
    }

    private void atualizarCursor() {
        // posições Y do cursor para cada slot
        double[] posicoesY = {170, 300, 430};
        cursor.setY(posicoesY[slotSelecionado]);
    }

    private void selecionarSlot() {
        System.out.println("Slot " + (slotSelecionado + 1) + " selecionado");
        App.setSlotAtivo(slotSelecionado);

        if (App.isModoNovoJogo()) {
            // NOVO JOGO - ignora save anterior, cria novo
            App.setJogoController(new JogoController());
            System.out.println("Novo jogo criado no slot " + (slotSelecionado + 1));
        } else {
            // CARREGAR - tenta carregar save existente
            Jogo jogoCarregado = GerenciadorDeSaves.carregar(slotSelecionado);
            if (jogoCarregado != null) {
                App.setJogoController(new JogoController(jogoCarregado));
                System.out.println("Partida carregada do slot " + (slotSelecionado + 1));
            } else {
                System.out.println("Nenhuma partida neste slot");
            }
        }

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