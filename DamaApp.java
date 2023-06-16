package com.example.jogodama;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DamaApp extends Application {

    public static final int TAMANHO_QUADRICULADO = 100;
    public static final int LARGURA = 8;
    public static final int ALTURA = 8;

    private final Quadriculado[][] tabuleiro = new Quadriculado[LARGURA][ALTURA];

    private final Group quadriculadoGroup = new Group();
    private final Group pecaGroup = new Group();

    private Parent criarConteudo() {
        Pane painel = new Pane();
        painel.setPrefSize(LARGURA * TAMANHO_QUADRICULADO, ALTURA * TAMANHO_QUADRICULADO);
        painel.getChildren().addAll(quadriculadoGroup, pecaGroup);

        for (int i = 0; i < ALTURA; i++) {
            for (int j = 0; j < LARGURA; j++) {
                Quadriculado quadriculado = new Quadriculado((j + i) % 2 == 0, j, i);
                tabuleiro[j][i] = quadriculado;

                quadriculadoGroup.getChildren().add(quadriculado);

                Peca peca = null;

                if (i <= 2 && (j + i) % 2 != 0) {
                    peca = fazerPeca(TipoPeca.VERMELHA, j, i);
                }

                if (i >= 5 && (j + i) % 2 != 0) {
                    peca = fazerPeca(TipoPeca.BRANCA, j, i);
                }
                if (peca != null) {
                    quadriculado.setPeca(peca);
                    pecaGroup.getChildren().add(peca);
                }
            }
        }
        return painel;
    }

    private AcaoMovimento tentarMover(Peca peca, int newX, int newY) {
        if (tabuleiro[newX][newY].temPeca() || (newX + newY) % 2 == 0) {
            return new AcaoMovimento(TipoMovimento.NENHUM);
        }

        int x0 = aoTabuleiro(peca.getOldX());
        int y0 = aoTabuleiro(peca.getOldY());

        if (Math.abs(newX - x0) == 1 && newY - y0 == peca.getTipo().direcaoMovimento) {
            return new AcaoMovimento(TipoMovimento.NORMAL);
        } else if (Math.abs(newX - x0) == 2 && newY - y0 == peca.getTipo().direcaoMovimento * 2) {
            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (tabuleiro[x1][y1].temPeca() && tabuleiro[x1][y1].getPeca().getTipo() != peca.getTipo()) {
                return new AcaoMovimento(TipoMovimento.COMER, tabuleiro[x1][y1].getPeca());
            }
        }
        return new AcaoMovimento(TipoMovimento.NENHUM);
    }

    private int aoTabuleiro(double pixel) {
        return (int) ((pixel + TAMANHO_QUADRICULADO / 2) / TAMANHO_QUADRICULADO);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(criarConteudo());
        stage.setTitle("Jogo de Damas");
        stage.setScene(scene);
        stage.show();
    }

    private Peca fazerPeca(TipoPeca tipo, int x, int y) {
        Peca peca = new Peca(tipo, x, y);

        peca.setOnMouseReleased(e -> {
            int newX = aoTabuleiro(peca.getLayoutX());
            int newY = aoTabuleiro(peca.getLayoutY());

            AcaoMovimento acao = tentarMover(peca, newX, newY);

            int x0 = aoTabuleiro(peca.getOldX());
            int y0 = aoTabuleiro(peca.getOldY());

            switch (acao.getTipo()) {
                case NENHUM -> {
                    peca.perderMovimento();
                }
                case NORMAL -> {
                    peca.movimento(newX, newY);
                    tabuleiro[x0][y0].setPeca(null);
                    tabuleiro[newX][newY].setPeca(peca);
                }
                case COMER -> {
                    peca.movimento(newX, newY);
                    tabuleiro[x0][y0].setPeca(null);
                    tabuleiro[newX][newY].setPeca(peca);

                    Peca outraPeca = acao.getPeca();
                    tabuleiro[aoTabuleiro(outraPeca.getOldX())][aoTabuleiro(outraPeca.getOldY())].setPeca(null);
                    pecaGroup.getChildren().remove(outraPeca);
                }
            }
        });

        return peca;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
