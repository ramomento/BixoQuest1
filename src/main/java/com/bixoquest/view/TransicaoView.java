package com.bixoquest.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TransicaoView {

    private static final int DURACAO_FADE = 300; // milissegundos

    /**
     * Faz transição suave (fade) entre cenas
     * @param stage janela principal
     * @param cenaDestino a nova scene a exibir
     */
    public static void executarFade(Stage stage, Scene cenaDestino) {
        Scene cenaAtual = stage.getScene();

        // se é a primeira cena, não faz animação
        if (cenaAtual == null) {
            stage.setScene(cenaDestino);
            return;
        }

        // fade out da cena atual
        Timeline fadeOut = new Timeline(
                new KeyFrame(Duration.millis(DURACAO_FADE),
                        event -> cenaAtual.getRoot().setOpacity(0))
        );

        fadeOut.setOnFinished(event -> {
            // troca para cena destino
            stage.setScene(cenaDestino);
            cenaDestino.getRoot().setOpacity(0);

            // fade in da nova cena
            Timeline fadeIn = new Timeline(
                    new KeyFrame(Duration.millis(DURACAO_FADE),
                            event2 -> cenaDestino.getRoot().setOpacity(1))
            );
            fadeIn.play();
        });

        fadeOut.play();
    }
}