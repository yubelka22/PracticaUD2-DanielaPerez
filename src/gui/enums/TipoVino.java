package gui.enums;

public enum TipoVino {
    TINTO("tinto"),
    BLANCO("blanco"),
    ROSADO("rosado"),
    ESPUMOSO("espumoso"),
    DULCE("dulce");

    private String valor;

    TipoVino(String valor) {this.valor = valor;}

    public String getValor() {return valor;}
}