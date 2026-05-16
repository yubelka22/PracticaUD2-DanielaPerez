package gui.enums;

public enum EstadoVenta {
    PENDIENTE("pendiente"),
    COMPLETADA("completada"),
    CANCELADA("cancelada");

    private String valor;

    EstadoVenta(String valor) {this.valor = valor;}

    public String getValor() {return valor;}
}