package com.bixoquest.view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class MenuView {

    private int opcaoSelecionada = 0; // 0 = Novo Jogo, 1 = Carregar, 2 = Sair
    private ImageView cursor;
    private Scene scene;

    public Scene criarCena() {
        Pane pane = new Pane();
        pane.setPrefSize(800, 600);

        ImageView fundo = carregarImagem("/images/ui/menu_background.png", 0, 0, 800, 560);
        pane.getChildren().add(fundo);

        // cursor que indica qual opção está selecionada
        cursor = carregarImagem("/images/ui/cursor.png", 85, 495, 30, 30);
        pane.getChildren().add(cursor);

        scene = new Scene(pane);
        scene.setFill(Color.BLACK);

        // configura o listener de teclado
        configurarTeclado();

        return scene;
    }

    private void configurarTeclado() {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();

            if (code == KeyCode.LEFT) {
                if (opcaoSelecionada > 0) {
                    opcaoSelecionada--;
                    atualizarCursor();
                }
            } else if (code == KeyCode.RIGHT) {
                if (opcaoSelecionada < 2) {
                    opcaoSelecionada++;
                    atualizarCursor();
                }
            } else if (code == KeyCode.ENTER) {
                selecionarOpcao();
            }

            event.consume();
        });
    }

    private void atualizarCursor() {
        // posições X do cursor para cada opção
        double[] posicoesX = {85, 280, 490};
        cursor.setX(posicoesX[opcaoSelecionada]);
    }

    private void selecionarOpcao() {
        switch (opcaoSelecionada) {
            case 0: // Novo Jogo
                App.mostrarSelecaoSlots();
                break;
            case 1: // Carregar
                App.mostrarSelecaoSlots();
                break;
            case 2: // Sair
                System.exit(0);
                break;
        }
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