package utp.ac.pa.sistema.domain;

import java.time.LocalDate;
import java.util.List;

import utp.ac.pa.sistema.utils.IOUtils;

/**
 * Representa una solicitud de mantenimiento hecha por un inquilino.
 */
public class SolicitudMantenimiento {

    private String idSolicitud;
    private ContratoAlquiler contrato;
    private Tecnico tecnicoAsignado;
    private String descripcionProblema;
    private LocalDate fechaSolicitud;
    private LocalDate fechaAtencion;
    private EstadoSolicitud estado;

    public SolicitudMantenimiento(String idSolicitud, ContratoAlquiler contrato,
                                  String descripcionProblema)
            throws ValidacionException {
        setIdSolicitud(idSolicitud);
        setContrato(contrato);
        setDescripcionProblema(descripcionProblema);
        this.fechaSolicitud = LocalDate.now();
        this.estado = EstadoSolicitud.PENDIENTE;
    }

    private void validarTextoObligatorio(String valor, String campo) throws ValidacionException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException("El campo " + campo + " es obligatorio.");
        }
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) throws ValidacionException {
        validarTextoObligatorio(idSolicitud, "id de solicitud");
        this.idSolicitud = idSolicitud.trim();
    }

    public ContratoAlquiler getContrato() {
        return contrato;
    }

    public void setContrato(ContratoAlquiler contrato) throws ValidacionException {
        if (contrato == null) {
            throw new ValidacionException("El contrato de la solicitud es obligatorio.");
        }
        this.contrato = contrato;
    }

    public Tecnico getTecnicoAsignado() {
        return tecnicoAsignado;
    }

    /**
     * Asigna un técnico y cambia el estado a EN_CURSO.
     */
    public void asignarTecnico(Tecnico tecnicoAsignado) throws ValidacionException {
        if (tecnicoAsignado == null) {
            throw new ValidacionException("El técnico asignado no puede ser nulo.");
        }
        this.tecnicoAsignado = tecnicoAsignado;
        this.estado = EstadoSolicitud.EN_CURSO;
    }

    public String getDescripcionProblema() {
        return descripcionProblema;
    }

    public void setDescripcionProblema(String descripcionProblema) throws ValidacionException {
        validarTextoObligatorio(descripcionProblema, "descripción del problema");
        this.descripcionProblema = descripcionProblema.trim();
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public LocalDate getFechaAtencion() {
        return fechaAtencion;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    /**
     * Marca la solicitud como resuelta y registra la fecha de atención.
     */
    public void marcarResuelta() {
        this.estado = EstadoSolicitud.RESUELTA;
        this.fechaAtencion = LocalDate.now();
    }

    /**
     * Cancela la solicitud.
     */
    public void cancelar() {
        this.estado = EstadoSolicitud.CANCELADA;
    }

    /**
     * Flujo interactivo para registrar una solicitud de mantenimiento.
     * No se dispone de la lista de contratos en este metodo (ver Main),
     * por lo que se crea un contrato ligero con los datos proporcionados.
     */
    public static void registrarSolicitud(List<Propiedad> propiedades,
                                          List<Inquilino> inquilinos,
                                          List<SolicitudMantenimiento> tickets) {
        if (propiedades.isEmpty() || inquilinos.isEmpty()) {
            IOUtils.warn("Datos faltantes",
                    "Debe haber al menos una propiedad y un inquilino registrados.");
            return;
        }
        try {
            String propId = IOUtils.askNonEmpty("Solicitud", "ID de la propiedad");
            Propiedad prop = propiedades.stream()
                    .filter(p -> p.getIdPropiedad().equalsIgnoreCase(propId))
                    .findFirst()
                    .orElse(null);
            if (prop == null) {
                IOUtils.warn("No encontrada", "Propiedad no encontrada.");
                return;
            }

            String inqId = IOUtils.askNonEmpty("Solicitud", "ID del inquilino");
            Inquilino inq = inquilinos.stream()
                    .filter(i -> i.getId().equalsIgnoreCase(inqId))
                    .findFirst()
                    .orElse(null);
            if (inq == null) {
                IOUtils.warn("No encontrado", "Inquilino no encontrado.");
                return;
            }

            String descripcion = IOUtils.askNonEmpty("Solicitud",
                    "Describa el problema de mantenimiento");

            // Contrato ligero para asociar la solicitud
            LocalDate inicio = LocalDate.now();
            LocalDate fin = inicio.plusMonths(12);
            double monto = prop.calcularPrecioTotalMensual();
            ContratoAlquiler contratoLigero = new ContratoAlquiler(
                    "CT-" + System.currentTimeMillis(), prop, inq, inicio, fin, monto, 5);

            String idTicket = "TCK-" + (tickets.size() + 1);
            SolicitudMantenimiento ticket = new SolicitudMantenimiento(
                    idTicket, contratoLigero, descripcion);
            tickets.add(ticket);
            IOUtils.info("OK", "Solicitud registrada con ID " + idTicket);
        } catch (ValidacionException e) {
            IOUtils.warn("Error", e.getMessage());
        }
    }

    /**
     * Permite cambiar el estado de una solicitud.
     */
    public static void cambiarEstadoInteractivo(List<SolicitudMantenimiento> tickets) {
        if (tickets.isEmpty()) {
            IOUtils.info("Solicitudes", "No hay solicitudes registradas.");
            return;
        }
        String id = IOUtils.askNonEmpty("Ticket", "ID de la solicitud (TCK-..)");
        SolicitudMantenimiento ticket = tickets.stream()
                .filter(t -> t.getIdSolicitud().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
        if (ticket == null) {
            IOUtils.warn("No encontrada", "Solicitud no encontrada.");
            return;
        }

        String opcion = IOUtils.askNonEmpty("Actualizar solicitud",
                "1=Asignar tecnico, 2=Marcar resuelta, 3=Cancelar");
        try {
            switch (opcion) {
                case "1" -> {
                    String techId = IOUtils.askNonEmpty("Tecnico", "ID");
                    String techNombre = IOUtils.askSoloLetras("Tecnico", "Nombre completo");
                    String techEmail = IOUtils.askEmail("Tecnico", "Email");
                    String techTel = IOUtils.askSoloDigitos("Tecnico", "Telefono");
                    String techUser = IOUtils.askNonEmpty("Tecnico", "Usuario");
                    String techPass = IOUtils.askNonEmpty("Tecnico", "Contrasena (min 6 chars)");
                    String techEsp = IOUtils.askNonEmpty("Tecnico", "Especialidad");
                    Tecnico tecnico = new Tecnico(
                            techId, techNombre, techEmail, techTel, techUser, techPass, techEsp);
                    ticket.asignarTecnico(tecnico);
                    IOUtils.info("OK", "Tecnico asignado.");
                }
                case "2" -> {
                    ticket.marcarResuelta();
                    IOUtils.info("OK", "Solicitud marcada como resuelta.");
                }
                case "3" -> {
                    ticket.cancelar();
                    IOUtils.info("OK", "Solicitud cancelada.");
                }
                default -> IOUtils.warn("Opcion invalida", "No se realizaron cambios.");
            }
        } catch (ValidacionException e) {
            IOUtils.warn("Error", e.getMessage());
        }
    }
}
