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
    private LocalDate fechaSolicitud = LocalDate.now();
    private Estado estado = Estado.REGISTRADA;
    private Tecnico asignado;

    public SolicitudMantenimiento(String id, Propiedad propiedad,
                                  Inquilino solicitadoPor, String descripcion) {
        this.id = id;
        this.propiedad = propiedad;
        this.solicitadoPor = solicitadoPor;
        this.descripcion = descripcion;
    }

    public void asignarTecnico(Tecnico t) {
        this.asignado = t;
        this.estado = Estado.EN_PROCESO;
    }

    public void marcarResuelta() { this.estado = Estado.RESUELTA; }

    /**
     * Caso de uso: registrar una nueva solicitud de mantenimiento.
     * Aquí se usa explícitamente BufferedReader para la descripción.
     */
    public static void registrarSolicitud(List<Propiedad> propiedades,
                                          List<Inquilino> inquilinos,
                                          List<SolicitudMantenimiento> tickets) {

        if (propiedades.isEmpty() || inquilinos.isEmpty()) {
            IOUtils.warn("Faltan datos",
                    "Debe existir al menos una propiedad y un inquilino.");
            return;
        }

        String id = IOUtils.ask("Ticket", "ID del ticket:");
        String propId = IOUtils.ask("Ticket", "ID de la propiedad:");
        Propiedad prop = propiedades.stream()
                .filter(p -> p.getId().equals(propId))
                .findFirst()
                .orElse(null);

        if (prop == null) {
            IOUtils.warn("Error", "Propiedad no encontrada.");
            return;
        }

        String inqId = IOUtils.ask("Ticket", "ID del inquilino:");
        Inquilino inq = inquilinos.stream()
                .filter(i -> i.id.equals(inqId))
                .findFirst()
                .orElse(null);

        if (inq == null) {
            IOUtils.warn("Error", "Inquilino no encontrado.");
            return;
        }

        // Aquí entra el BufferedReader
        String desc = IOUtils.readLineConsole(
                "Escriba la descripción del problema en la consola");
        if (desc == null || desc.isBlank()) desc = "Sin descripción";

        SolicitudMantenimiento sm = new SolicitudMantenimiento(id, prop, inq, desc);
        tickets.add(sm);

        IOUtils.info("OK", "Solicitud registrada:\n" + sm);
    }

    @Override
    public String toString() {
        return "Ticket{" + id + ", prop=" + propiedad.getId() +
                ", por=" + solicitadoPor.getNombreUsuario() +
                ", estado=" + estado + "}";
    }
}
