package com.example.jogodama;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Quadriculado extends Rectangle {

    private Peca peca;

    public boolean temPeca() {
        return peca != null;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public Quadriculado(boolean light, int x, int y) {
        setWidth(DamaApp.TAMANHO_QUADRICULADO);
        setHeight(DamaApp.TAMANHO_QUADRICULADO);

        relocate(x * DamaApp.TAMANHO_QUADRICULADO, y * DamaApp.TAMANHO_QUADRICULADO);

        setFill(light ? Color.valueOf("#feb") : Color.valueOf("#582"));
    }

}
