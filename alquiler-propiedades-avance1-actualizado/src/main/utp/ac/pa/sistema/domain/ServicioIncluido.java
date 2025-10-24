package utp.ac.pa.sistema.domain;

public class ServicioIncluido {
    public enum Tipo { AGUA, LUZ, GAS, INTERNET, ASEO }
    private Tipo tipo;
    private boolean incluido;

    public ServicioIncluido(Tipo tipo, boolean incluido) {
        this.tipo = tipo;
        this.incluido = incluido;
    }
}
