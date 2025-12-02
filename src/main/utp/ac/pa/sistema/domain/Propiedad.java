package utp.ac.pa.sistema.domain;

import utp.ac.pa.sistema.utils.IOUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Modela una propiedad en alquiler (casa, apartamento, local, etc.).
 */
public class Propiedad {

    public enum Tipo { CASA, APARTAMENTO, LOCAL, OFICINA }
    public enum Estado { DISPONIBLE, ALQUILADA }

    private String id;
    private Tipo tipo;
    private double areaM2;
    private int habitaciones;
    private double precioMensual;
    private Direccion direccion;
    private List<ServicioIncluido> servicios = new ArrayList<>();
    private Estado estado;

    public Propiedad(String id, Tipo tipo, Direccion direccion) {
        this.id = id;
        this.tipo = tipo;
        this.direccion = direccion;
        this.estado = Estado.DISPONIBLE;
    }

    public Propiedad(String id, Tipo tipo, double areaM2, int habitaciones,
                     double precioMensual, Direccion direccion) {
        this.id = id;
        this.tipo = tipo;
        this.areaM2 = areaM2;
        this.habitaciones = habitaciones;
        this.precioMensual = precioMensual;
        this.direccion = direccion;
        this.estado = Estado.DISPONIBLE;
    }

    public String getId() {
        return id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public double getAreaM2() {
        return areaM2;
    }

    public int getHabitaciones() {
        return habitaciones;
    }

    public double getPrecioMensual() {
        return precioMensual;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public List<ServicioIncluido> getServicios() {
        return servicios;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void agregarServicio(ServicioIncluido servicio) {
        if (servicio != null) {
            servicios.add(servicio);
        }
    }

    public static Propiedad buscarPorId(List<Propiedad> lista, String id) {
        for (Propiedad p : lista) {
            if (p.id.equals(id)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Registro interactivo de una propiedad.
     */
    public static void registrarPropiedad(List<Propiedad> lista) {

        // ID único
        String id;
        while (true) {
            id = IOUtils.askNonEmpty("Propiedad", "ID de la propiedad:");
            if (buscarPorId(lista, id) != null) {
                IOUtils.warn("ID duplicado",
                        "Ya existe una propiedad con ese ID. Ingrese uno diferente.");
            } else {
                break;
            }
        }

        // Tipo de propiedad
        Tipo tipo = null;
        while (tipo == null) {
            String tipoStr = IOUtils.askNonEmpty("Propiedad",
                    "Tipo (CASA / APARTAMENTO / LOCAL / OFICINA):").toUpperCase();
            try {
                tipo = Tipo.valueOf(tipoStr);
            } catch (IllegalArgumentException e) {
                IOUtils.warn("Tipo inválido",
                        "Debe ser CASA, APARTAMENTO, LOCAL u OFICINA.");
            }
        }

        // Datos numéricos
        double area = IOUtils.askDouble("Propiedad", "Área en m²:");
        if (area < 0) {
            IOUtils.warn("Valor ajustado", "El área no puede ser negativa. Se guardará como 0.");
            area = 0;
        }

        int habitaciones = IOUtils.askInt("Propiedad", "Número de habitaciones:");
        if (habitaciones < 0) {
            IOUtils.warn("Valor ajustado",
                    "Las habitaciones no pueden ser negativas. Se guardará como 0.");
            habitaciones = 0;
        }

        double precio = IOUtils.askDouble("Propiedad", "Precio mensual de alquiler:");
        if (precio <= 0) {
            IOUtils.warn("Precio inválido",
                    "El precio debe ser mayor a 0. Se guardará como 1.");
            precio = 1;
        }

        // Dirección (simplificada)
        String ciudad = IOUtils.askNonEmpty("Propiedad", "Ciudad:");
        String barrio = IOUtils.askNonEmpty("Propiedad", "Barrio:");
        String calle = IOUtils.askNonEmpty("Propiedad", "Calle:");
        String numero = IOUtils.askNonEmpty("Propiedad", "Número:");

        Direccion dir = new Direccion("Panamá", "Panamá",
                ciudad, barrio, calle, numero, "0000");

        Propiedad p = new Propiedad(id, tipo, area, habitaciones, precio, dir);
        p.estado = Estado.DISPONIBLE;
        lista.add(p);

        IOUtils.info("OK", "Propiedad registrada:\n" + p);
    }

    /**
     * Lista las propiedades que están en estado DISPONIBLE.
     */
    public static void listarDisponibles(List<Propiedad> lista) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== PROPIEDADES DISPONIBLES ===\n");
        int contador = 0;
        for (Propiedad p : lista) {
            if (p.estado == Estado.DISPONIBLE) {
                contador++;
                sb.append("- ID: ").append(p.id)
                  .append(" | Tipo: ").append(p.tipo)
                  .append(" | Dirección: ").append(p.direccion.getFormatoCorto())
                  .append(" | Precio: $").append(String.format("%.2f", p.precioMensual))
                  .append("\n");
            }
        }
        if (contador == 0) {
            sb.append("No hay propiedades disponibles actualmente.\n");
        }
        IOUtils.info("Propiedades disponibles", sb.toString());
    }

    @Override
    public String toString() {
        return "Propiedad{" + id + ", " + tipo + ", " +
                direccion.getFormatoCorto() + ", estado=" + estado + "}";
    }
}
