package utp.ac.pa.sistema.domain;

/**
 * Dirección física de una propiedad.
 */
public class Direccion {

    private String pais;
    private String provincia;
    private String ciudad;
    private String barrio;
    private String calle;
    private String numero;
    public Direccion(String pais, String provincia, String ciudad,
                     String barrio, String calle, String numero,
                     String codigoPostal) {
        this.pais = pais;
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.barrio = barrio;
        this.calle = calle;
        this.numero = numero;
    }

    public String getCiudad() { 
        return ciudad; 
    }

    public String getBarrio() {
         return barrio;
         }

    public String getFormatoCorto() {
        return ciudad + ", " + barrio + " - " + calle + " " + numero;
    }

    @Override
    public String toString() {
        return getFormatoCorto() + " (" + provincia + ", " + pais + ")";
    }
}
