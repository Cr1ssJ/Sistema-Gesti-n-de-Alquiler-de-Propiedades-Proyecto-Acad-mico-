package utp.ac.pa.sistema.domain;

import utp.ac.pa.sistema.utils.IOUtils;
import java.util.List;

/**
 * Clase responsable de generar reportes del sistema.
 */
public class GestorReportes {

    public static void mostrarResumen(List<Propiedad> propiedades,
                                      List<Inquilino> inquilinos,
                                      List<ContratoAlquiler> contratos,
                                      List<Pago> pagos,
                                      List<SolicitudMantenimiento> tickets) {

        int contratosActivos = 0;
        int contratosFinalizados = 0;
        int contratosCancelados = 0;

        for (ContratoAlquiler c : contratos) {
            if (c.getEstado() == ContratoAlquiler.Estado.ACTIVO) {
                contratosActivos++;
            } else if (c.getEstado() == ContratoAlquiler.Estado.FINALIZADO) {
                contratosFinalizados++;
            } else if (c.getEstado() == ContratoAlquiler.Estado.CANCELADO) {
                contratosCancelados++;
            }
        }

        int ticketsRegistrados = 0;
        int ticketsEnProceso = 0;
        int ticketsResueltos = 0;

        for (SolicitudMantenimiento t : tickets) {
            if (t.getEstado() == SolicitudMantenimiento.Estado.REGISTRADA) {
                ticketsRegistrados++;
            } else if (t.getEstado() == SolicitudMantenimiento.Estado.EN_PROCESO) {
                ticketsEnProceso++;
            } else if (t.getEstado() == SolicitudMantenimiento.Estado.RESUELTA) {
                ticketsResueltos++;
            }
        }

        double totalCobrado = 0;
        for (Pago p : pagos) {
            totalCobrado += p.getMonto();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== RESUMEN GENERAL ===\n");
        sb.append("Propiedades: ").append(propiedades.size()).append("\n");
        sb.append("Inquilinos: ").append(inquilinos.size()).append("\n");
        sb.append("Contratos: ").append(contratos.size()).append("\n");
        sb.append("Pagos registrados: ").append(pagos.size()).append("\n");
        sb.append("Tickets de mantenimiento: ").append(tickets.size()).append("\n\n");

        sb.append("=== CONTRATOS POR ESTADO ===\n");
        sb.append("Activos: ").append(contratosActivos).append("\n");
        sb.append("Finalizados: ").append(contratosFinalizados).append("\n");
        sb.append("Cancelados: ").append(contratosCancelados).append("\n\n");

        sb.append("=== TICKETS POR ESTADO ===\n");
        sb.append("Registrados: ").append(ticketsRegistrados).append("\n");
        sb.append("En proceso: ").append(ticketsEnProceso).append("\n");
        sb.append("Resueltos: ").append(ticketsResueltos).append("\n\n");

        sb.append("Monto total cobrado en pagos: $")
          .append(String.format("%.2f", totalCobrado)).append("\n\n");

        // Últimos contratos
        if (!contratos.isEmpty()) {
            sb.append("=== ÚLTIMOS CONTRATOS ===\n");
            int inicio = Math.max(0, contratos.size() - 3);
            for (int i = inicio; i < contratos.size(); i++) {
                ContratoAlquiler c = contratos.get(i);
                sb.append("- [").append(c.getId()).append("] ")
                  .append(c.getFechaInicio()).append(" -> ")
                  .append(c.getFechaFin())
                  .append(" | Propiedad: ").append(c.getPropiedad().getId())
                  .append(" | Inquilino: ").append(c.getInquilino().getNombreUsuario())
                  .append(" | Estado: ").append(c.getEstado())
                  .append("\n");
            }
            sb.append("\n");
        }

        // Últimos tickets
        if (!tickets.isEmpty()) {
            sb.append("=== ÚLTIMOS TICKETS ===\n");
            int inicio = Math.max(0, tickets.size() - 3);
            for (int i = inicio; i < tickets.size(); i++) {
                SolicitudMantenimiento t = tickets.get(i);
                sb.append("- [").append(t.getId()).append("] ")
                  .append(t.getFechaCreacion()).append(" | Propiedad: ")
                  .append(t.getPropiedad().getId())
                  .append(" | Estado: ").append(t.getEstado())
                  .append("\n  Descripción: ").append(t.getDescripcion())
                  .append("\n");
            }
        }

        IOUtils.info("Resumen del sistema", sb.toString());
    }

}
