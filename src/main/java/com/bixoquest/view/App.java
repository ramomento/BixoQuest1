// TERCEIRA: App.java - SIMPLIFICAR
package com.bixoquest.view;

import com.bixoquest.controller.JogoController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class App extends Application {

    private static Stage primaryStage;
    private static JogoController jogoController;
    private static int slotAtivo = 0;
    private static boolean modoNovoJogo = false;

    public static int getSlotAtivo() { return slotAtivo; }
    public static void setSlotAtivo(int slot) { slotAtivo = slot; }
    public static void setJogoController(JogoController controller) {
        jogoController = controller;
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        jogoController = new JogoController();

        GameFont.carregar();

        stage.setTitle("BixoQuest");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setResizable(false);

        mostrarMenuInicial();

        stage.show();
    }

    public static void mostrarMenuInicial() {
        MenuView menuView = new MenuView();
        Scene scene = menuView.criarCena();
        scene.setFill(Color.BLACK);
        TransicaoView.executarFade(primaryStage, scene);
    }

    public static void mostrarSelecaoSlots(boolean novoJogo) {
        modoNovoJogo = novoJogo;
        SelecaoSlotView view = new SelecaoSlotView();
        TransicaoView.executarFade(primaryStage, view.criarCena());
    }

    public static boolean isModoNovoJogo() { return modoNovoJogo; }

    public static void mostrarTelaPrincipal() {
        TelaPrincipalView view = new TelaPrincipalView(jogoController);
        TransicaoView.executarFade(primaryStage, view.criarCena());
    }

    public static void main(String[] args) {
        launch(args);
    }
}