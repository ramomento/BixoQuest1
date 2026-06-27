package com.bixoquest.view;

import javafx.scene.text.Font;

public class GameFont {
    private static Font small;
    private static Font medium;
    private static Font large;

    public static void carregar() {
        small  = Font.loadFont(GameFont.class.getResourceAsStream("/fonts/Jersey15-Regular.ttf"), 18);
        medium = Font.loadFont(GameFont.class.getResourceAsStream("/fonts/Jersey15-Regular.ttf"), 24);
        large  = Font.loadFont(GameFont.class.getResourceAsStream("/fonts/Jersey15-Regular.ttf"), 34);
    }

    public static Font small()  { return small; }
    public static Font medium() { return medium; }
    public static Font large()  { return large; }
}