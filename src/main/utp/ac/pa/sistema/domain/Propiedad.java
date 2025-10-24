package utp.ac.pa.sistema.domain;

import java.util.ArrayList;
import java.util.List;

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

    public void agregarServicio(ServicioIncluido s){ servicios.add(s); }
    public List<ServicioIncluido> getServicios(){ return servicios; }
    public Direccion getDireccion(){ return direccion; }
    public String getId(){ return id; }
}
