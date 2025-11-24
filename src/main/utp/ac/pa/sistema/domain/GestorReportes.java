package utp.ac.pa.sistema.domain;

import utp.ac.pa.sistema.utils.IOUtils;
import java.util.List;

/**
 * Clase responsable de generar reportes del sistema.
 */
public class GestorReportes {

    /**
     * Genera un resumen general para mostrar en una ventana.
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
        sb.append("Tickets: ").append(tickets.size()).append("\n");

        IOUtils.info("Resumen del sistema", sb.toString());
    }

}
