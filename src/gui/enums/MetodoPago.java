package gui.enums;

public enum MetodoPago {
    EFECTIVO("efectivo"),
    TARJETA("tarjeta"),
    BIZUM("bizum");

    private String valor;

    MetodoPago(String valor) {this.valor = valor;}

    public String getValor() {return valor;}
}
