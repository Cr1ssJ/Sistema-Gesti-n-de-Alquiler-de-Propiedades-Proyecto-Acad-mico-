package utp.ac.pa.sistema.domain;

/**
 * Representa un servicio (agua, luz, etc.) asociado a una propiedad.
 */
public class ServicioIncluido {

    public enum Tipo { AGUA, LUZ, GAS, INTERNET, ASEO }

    private Tipo tipo;
    private boolean incluido;

    public ServicioIncluido(Tipo tipo, boolean incluido) {
        this.tipo = tipo;
        this.incluido = incluido;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public boolean isIncluido() {
        return incluido;
    }

    @Override
    public String toString() {
        return tipo + (incluido ? " (incluido)" : " (no incluido)");
    }
}
