package utp.ac.pa.sistema.domain;

import java.util.List;

import utp.ac.pa.sistema.utils.IOUtils;

/**
 * Servicio encargado de generar diferentes reportes de texto
 * sobre contratos, pagos y solicitudes.
 */
public class GestorReportes {

    /**
     * Genera un reporte de contratos filtrados por estado.
     */
    public String generarReporteContratosPorEstado(EstadoContrato estado,
                                                   List<ContratoAlquiler> contratos) {
        StringBuilder sb = new StringBuilder();
        sb.append("REPORTE DE CONTRATOS - ESTADO: ").append(estado).append("\n");
        sb.append("=======================================\n");
        for (ContratoAlquiler c : contratos) {
            if (c.getEstado() == estado) {
                sb.append("Contrato: ").append(c.getIdContrato())
                  .append(" | Propiedad: ").append(c.getPropiedad().getIdPropiedad())
                  .append(" | Inquilino: ").append(c.getInquilino().getNombreCompleto())
                  .append(" | Monto Mensual: ").append(c.getMontoMensual())
                  .append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Genera un reporte de pagos asociados a un contrato.
     */
    public String generarReportePagosPorContrato(ContratoAlquiler contrato) {
        StringBuilder sb = new StringBuilder();
        sb.append("REPORTE DE PAGOS - CONTRATO: ").append(contrato.getIdContrato()).append("\n");
        sb.append("=======================================\n");
        for (Pago p : contrato.getPagos()) {
            sb.append("Pago: ").append(p.getIdPago())
              .append(" | Fecha: ").append(p.getFechaPago())
              .append(" | Monto: ").append(p.getMonto())
              .append(" | Método: ").append(p.getMetodoPago())
              .append(" | A tiempo: ").append(p.isATiempo())
              .append("\n");
        }
        return sb.toString();
    }

    /**
     * Genera un reporte de solicitudes de mantenimiento filtradas por estado.
     */
    public String generarReporteSolicitudesPorEstado(EstadoSolicitud estado,
                                                     List<SolicitudMantenimiento> solicitudes) {
        StringBuilder sb = new StringBuilder();
        sb.append("REPORTE DE SOLICITUDES - ESTADO: ").append(estado).append("\n");
        sb.append("=======================================\n");
        for (SolicitudMantenimiento s : solicitudes) {
            if (s.getEstado() == estado) {
                sb.append("Solicitud: ").append(s.getIdSolicitud())
                  .append(" | Contrato: ").append(s.getContrato().getIdContrato())
                  .append(" | Descripción: ").append(s.getDescripcionProblema())
                  .append(" | Fecha: ").append(s.getFechaSolicitud())
                  .append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Muestra un resumen rapido del sistema.
     */
    public static void mostrarResumen(List<Propiedad> propiedades,
                                      List<Inquilino> inquilinos,
                                      List<ContratoAlquiler> contratos,
                                      List<Pago> pagos,
                                      List<SolicitudMantenimiento> tickets) {
        StringBuilder sb = new StringBuilder();
        sb.append("Propiedades: ").append(propiedades.size()).append("\n");
        sb.append("Inquilinos: ").append(inquilinos.size()).append("\n");
        sb.append("Contratos: ").append(contratos.size()).append("\n");
        sb.append("Pagos: ").append(pagos.size()).append("\n");
        sb.append("Tickets: ").append(tickets.size()).append("\n\n");

        long contratosVigentes = contratos.stream()
                .filter(c -> c.getEstado() == EstadoContrato.VIGENTE).count();
        long contratosVencidos = contratos.stream()
                .filter(c -> c.getEstado() == EstadoContrato.VENCIDO).count();
        sb.append("Contratos vigentes: ").append(contratosVigentes).append("\n");
        sb.append("Contratos vencidos: ").append(contratosVencidos).append("\n");

        long ticketsPendientes = tickets.stream()
                .filter(t -> t.getEstado() == EstadoSolicitud.PENDIENTE).count();
        sb.append("Tickets pendientes: ").append(ticketsPendientes).append("\n");

        IOUtils.info("Resumen del sistema", sb.toString());
    }
}
