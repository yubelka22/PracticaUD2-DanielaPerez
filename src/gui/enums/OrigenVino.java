package gui.enums;

public enum OrigenVino {
    ESPAÑA("España"),
    FRANCIA("Francia"),
    ITALIA("Italia"),
    ARGENTINA("Argentina"),
    CHILE("Chile");

    private String valor;

    OrigenVino(String valor) {this.valor = valor;}

    public String getValor() {return valor;}
}