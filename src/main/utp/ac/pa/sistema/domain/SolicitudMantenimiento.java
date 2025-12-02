package utp.ac.pa.sistema.domain;

import utp.ac.pa.sistema.utils.IOUtils;
import java.time.LocalDate;
import java.util.List;

/**
 * Representa una solicitud de mantenimiento sobre una propiedad.
 */
public class SolicitudMantenimiento {

    public enum Estado { REGISTRADA, EN_PROCESO, RESUELTA }

    private String id;
    private Propiedad propiedad;
    private Inquilino solicitadoPor;
    private String descripcion;
    private LocalDate fechaCreacion;
    private Estado estado;

    public SolicitudMantenimiento(String id,
                                  Propiedad propiedad,
                                  Inquilino solicitadoPor,
                                  String descripcion) {
        this.id = id;
        this.propiedad = propiedad;
        this.solicitadoPor = solicitadoPor;
        this.descripcion = descripcion;
        this.fechaCreacion = LocalDate.now();
        this.estado = Estado.REGISTRADA;
    }

    public String getId() {
        return id;
    }

    public Propiedad getPropiedad() {
        return propiedad;
    }

    public Inquilino getSolicitadoPor() {
        return solicitadoPor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void cambiarEstado(Estado nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public static SolicitudMantenimiento buscarPorId(
            List<SolicitudMantenimiento> tickets, String id) {
        for (SolicitudMantenimiento t : tickets) {
            if (t.id.equals(id)) {
                return t;
            }
        }
        return null;
    }

    public static void registrarSolicitud(List<Propiedad> propiedades,
                                          List<Inquilino> inquilinos,
                                          List<SolicitudMantenimiento> tickets) {

        if (propiedades.isEmpty() || inquilinos.isEmpty()) {
            IOUtils.warn("Datos insuficientes",
                    "Debe existir al menos una propiedad y un inquilino antes de registrar un ticket.");
            return;
        }

        // ID único
        String id;
        while (true) {
            id = IOUtils.askNonEmpty("Ticket", "ID del ticket:");
            if (buscarPorId(tickets, id) != null) {
                IOUtils.warn("ID duplicado",
                        "Ya existe una solicitud con ese ID. Ingrese otro.");
            } else {
                break;
            }
        }

        String idProp = IOUtils.askNonEmpty("Ticket", "ID de la propiedad:");
        Propiedad prop = Propiedad.buscarPorId(propiedades, idProp);
        if (prop == null) {
            IOUtils.warn("Propiedad no encontrada", "No existe una propiedad con ese ID.");
            return;
        }

        String idInq = IOUtils.askNonEmpty("Ticket",
                "ID del inquilino que reporta:");
        Inquilino inq = Inquilino.buscarPorId(inquilinos, idInq);
        if (inq == null) {
            IOUtils.warn("Inquilino no encontrado",
                    "No existe un inquilino con ese ID.");
            return;
        }

        String desc = IOUtils.askNonEmpty("Ticket", "Descripción del problema:");

        SolicitudMantenimiento sm = new SolicitudMantenimiento(id, prop, inq, desc);
        tickets.add(sm);

        IOUtils.info("OK", "Solicitud registrada:\n" + sm);
    }

    /**
     * Cambia manualmente el estado de un ticket.
     */
    public static void cambiarEstadoInteractivo(List<SolicitudMantenimiento> tickets) {
        if (tickets.isEmpty()) {
            IOUtils.warn("Sin tickets", "No hay solicitudes registradas.");
            return;
        }

        String id = IOUtils.askNonEmpty("Ticket", "ID del ticket a actualizar:");
        SolicitudMantenimiento t = buscarPorId(tickets, id);
        if (t == null) {
            IOUtils.warn("No encontrado", "No existe ticket con ese ID.");
            return;
        }

        String msg = "Ticket: " + t.id +
                "\nPropiedad: " + t.propiedad.getId() +
                "\nInquilino: " + t.solicitadoPor.getNombreUsuario() +
                "\nEstado actual: " + t.estado +
                "\n\nSeleccione nuevo estado:\n" +
                "1. REGISTRADA\n" +
                "2. EN_PROCESO\n" +
                "3. RESUELTA";

        int opcion = IOUtils.askInt("Cambio de estado", msg);
        switch (opcion) {
            case 1 -> t.cambiarEstado(Estado.REGISTRADA);
            case 2 -> t.cambiarEstado(Estado.EN_PROCESO);
            case 3 -> t.cambiarEstado(Estado.RESUELTA);
            default -> {
                IOUtils.warn("Opción inválida", "No se realizó ningún cambio.");
                return;
            }
        }

        IOUtils.info("Estado actualizado",
                "Nuevo estado del ticket " + t.id + ": " + t.estado);
    }

    @Override
    public String toString() {
        return "Ticket{" + id + ", prop=" + propiedad.getId() +
                ", por=" + solicitadoPor.getNombreUsuario() +
                ", estado=" + estado + "}";
    }
}
