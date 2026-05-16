package gui.enums;

public enum CargoEmpleado {
    DEPENDIENTE("dependiente"),
    CAJERO("cajero"),
    ENCARGADO("encargado"),
    GERENTE("gerente"),
    SUMILLER("sumiller");

    private String valor;

    CargoEmpleado(String valor) {this.valor = valor;}

    public String getValor() {return valor;}
}
