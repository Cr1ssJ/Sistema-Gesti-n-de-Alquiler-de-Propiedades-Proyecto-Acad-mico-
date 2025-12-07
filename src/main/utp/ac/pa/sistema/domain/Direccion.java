package utp.ac.pa.sistema.domain;

import java.util.Objects;

/**
 * Representa la dirección física de una propiedad.
 */
public class Direccion {

    // Atributos privados para proteger el estado del objeto
    private String pais;
    private String provincia;
    private String ciudad;
    private String direccionDetalle;

    /**
     * Constructor con validaciones.
     */
    public Direccion(String pais, String provincia, String ciudad, String direccionDetalle)
            throws ValidacionException {
        setPais(pais);
        setProvincia(provincia);
        setCiudad(ciudad);
        setDireccionDetalle(direccionDetalle);
    }

    // Método auxiliar para validar que un texto no sea nulo ni vacío
    private void validarTextoObligatorio(String valor, String campo) throws ValidacionException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException("El campo " + campo + " es obligatorio.");
        }
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) throws ValidacionException {
        validarTextoObligatorio(pais, "país");
        this.pais = pais.trim();
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) throws ValidacionException {
        validarTextoObligatorio(provincia, "provincia");
        this.provincia = provincia.trim();
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) throws ValidacionException {
        validarTextoObligatorio(ciudad, "ciudad");
        this.ciudad = ciudad.trim();
    }

    public String getDireccionDetalle() {
        return direccionDetalle;
    }

    public void setDireccionDetalle(String direccionDetalle) throws ValidacionException {
        validarTextoObligatorio(direccionDetalle, "dirección detallada");
        this.direccionDetalle = direccionDetalle.trim();
    }

    @Override
    public String toString() {
        return direccionDetalle + ", " + ciudad + ", " + provincia + ", " + pais;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Direccion)) return false;
        Direccion that = (Direccion) o;
        return Objects.equals(pais, that.pais) &&
               Objects.equals(provincia, that.provincia) &&
               Objects.equals(ciudad, that.ciudad) &&
               Objects.equals(direccionDetalle, that.direccionDetalle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pais, provincia, ciudad, direccionDetalle);
    }
}
