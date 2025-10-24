package utp.ac.pa.sistema.domain;

public class Direccion {
    private String pais;
    private String provincia;
    private String ciudad;
    private String barrio;
    private String calle;
    private String numero;
    private String codigoPostal;

    public Direccion(String pais, String provincia, String ciudad, String barrio, String calle, String numero, String codigoPostal) {
        this.pais = pais;
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.barrio = barrio;
        this.calle = calle;
        this.numero = numero;
        this.codigoPostal = codigoPostal;
    }

    public String getFormatoCorto(){
        return ciudad + ", " + barrio + " - " + calle + " " + numero;
    }
}
