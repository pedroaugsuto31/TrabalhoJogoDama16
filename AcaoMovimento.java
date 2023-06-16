package com.example.jogodama;

public class AcaoMovimento {
    private final TipoMovimento tipo;
    private final Peca peca;

    public AcaoMovimento(TipoMovimento tipo) {
        this(tipo, null);
    }

    public AcaoMovimento(TipoMovimento tipo, Peca peca) {
        this.tipo = tipo;
        this.peca = peca;
    }

    public TipoMovimento getTipo() {
        return tipo;
    }

    public Peca getPeca() {
        return peca;
    }
}
