package utp.ac.pa.sistema.domain;

/**
 * Representa un servicio que está incluido en el alquiler de una propiedad.
 * Ejemplo: Agua, Luz, Internet, Mantenimiento.
 */
public class ServicioIncluido {

    private String nombre;
    private String descripcion;
    private double costoMensual;

    public ServicioIncluido(String nombre, String descripcion, double costoMensual)
            throws ValidacionException {
        setNombre(nombre);
        setDescripcion(descripcion);
        setCostoMensual(costoMensual);
    }

    private void validarTextoObligatorio(String valor, String campo) throws ValidacionException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException("El campo " + campo + " es obligatorio.");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws ValidacionException {
        validarTextoObligatorio(nombre, "nombre del servicio");
        this.nombre = nombre.trim();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) throws ValidacionException {
        validarTextoObligatorio(descripcion, "descripción");
        this.descripcion = descripcion.trim();
    }

    public double getCostoMensual() {
        return costoMensual;
    }

    public void setCostoMensual(double costoMensual) throws ValidacionException {
        if (costoMensual < 0) {
            throw new ValidacionException("El costo mensual no puede ser negativo.");
        }
        this.costoMensual = costoMensual;
    }
}
