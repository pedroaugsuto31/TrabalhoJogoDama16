package com.example.jogodama;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static com.example.jogodama.DamaApp.TAMANHO_QUADRICULADO;

public class Peca extends StackPane {

    private final TipoPeca tipo;
    private double mouseX, mouseY;
    private double oldX, oldY;

    public TipoPeca getTipo() {
        return tipo;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Peca(TipoPeca tipo, int x, int y) {
        this.tipo = tipo;

        movimento(x, y);

        //Peças pretas
        Ellipse fundo = new Ellipse(TAMANHO_QUADRICULADO * 0.3125, TAMANHO_QUADRICULADO * 0.26);
        fundo.setFill(Color.BLACK);

        fundo.setStroke(Color.BLACK);
        fundo.setStrokeWidth(TAMANHO_QUADRICULADO * 0.03);

        fundo.setTranslateX((TAMANHO_QUADRICULADO - TAMANHO_QUADRICULADO * 0.3125 * 2) / 2);
        fundo.setTranslateY((TAMANHO_QUADRICULADO - TAMANHO_QUADRICULADO * 0.26 * 2) / 2 + TAMANHO_QUADRICULADO * 0.07);

        //Peças Vermelhas
        Ellipse ellipse = new Ellipse(TAMANHO_QUADRICULADO * 0.3125, TAMANHO_QUADRICULADO * 0.26);
        ellipse.setFill(tipo == TipoPeca.VERMELHA ? Color.valueOf("#c40003") : Color.valueOf("#fff9f4"));

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(TAMANHO_QUADRICULADO * 0.03);

        ellipse.setTranslateX((TAMANHO_QUADRICULADO - TAMANHO_QUADRICULADO * 0.3125 * 2) / 2);
        ellipse.setTranslateY((TAMANHO_QUADRICULADO - TAMANHO_QUADRICULADO * 0.26 * 2) / 2);

        getChildren().addAll(fundo, ellipse);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY));
    }

    public void movimento(int x, int y) {
        oldX = x * TAMANHO_QUADRICULADO;
        oldY = y * TAMANHO_QUADRICULADO;
        relocate(oldX, oldY);
    }

    public void perderMovimento() {
        relocate(oldX, oldY);
    }
}
