package com.example.jogodama;

public enum TipoPeca {
    VERMELHA(1), BRANCA(-1);

    final int direcaoMovimento;

    TipoPeca(int direcaoMovimento) {
        this.direcaoMovimento = direcaoMovimento;
    }
}
