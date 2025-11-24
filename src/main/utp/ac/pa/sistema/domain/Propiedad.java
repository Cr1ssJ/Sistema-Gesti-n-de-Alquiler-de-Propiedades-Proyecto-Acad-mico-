package utp.ac.pa.sistema.domain;

import utp.ac.pa.sistema.utils.IOUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Modela una propiedad en alquiler (casa, apartamento, local, etc.).
 */
public class Propiedad {

    public enum Tipo { CASA, APARTAMENTO, LOCAL, OFICINA }

    private String id;
    private Tipo tipo;
    private double areaM2;
    private int habitaciones;
    private int banos;
    private boolean amoblado;
    private Direccion direccion;
    private List<ServicioIncluido> servicios = new ArrayList<>();

    public Propiedad(String id, Tipo tipo, Direccion direccion) {
        this.id = id;
        this.tipo = tipo;
        this.direccion = direccion;
    }

    public String getId() { 
        return id; 
    }

    public Direccion getDireccion() { 
        return direccion;
     }

    public void agregarServicio(ServicioIncluido s) {
        servicios.add(s);
    }

    public List<ServicioIncluido> getServicios() {
        return servicios;
    }

    /**
     * Caso de uso: registrar una nueva propiedad.
     * Pide datos al usuario, construye Dirección y Propiedad
     * y las almacena en la lista proporcionada.
     */
    public static void registrarPropiedad(List<Propiedad> lista) {
        String id = IOUtils.ask("Propiedad", "ID de la propiedad:");
        String tipoStr = IOUtils.ask("Propiedad", "Tipo (CASA/APARTAMENTO/LOCAL/OFICINA):");
        Tipo tipo = Tipo.valueOf(tipoStr.toUpperCase().trim());

        String ciudad = IOUtils.ask("Dirección", "Ciudad:");
        String barrio = IOUtils.ask("Dirección", "Barrio:");
        String calle = IOUtils.ask("Dirección", "Calle:");
        String numero = IOUtils.ask("Dirección", "Número:");

        Direccion dir = new Direccion("Panamá", "Panamá",
                ciudad, barrio, calle, numero, "0000");

        Propiedad p = new Propiedad(id, tipo, dir);
        lista.add(p);

        IOUtils.info("OK", "Propiedad registrada:\n" + p);
    }

    @Override
    public String toString() {
        return "Propiedad{" + id + ", " + tipo + ", " + direccion.getFormatoCorto() + "}";
    }
}
