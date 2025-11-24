package utp.ac.pa.sistema;

import utp.ac.pa.sistema.domain.*;
import utp.ac.pa.sistema.utils.IOUtils;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // "Base de datos" en memoria (listas)
        List<Propiedad> propiedades = new ArrayList<>();
        List<Inquilino> inquilinos = new ArrayList<>();
        List<ContratoAlquiler> contratos = new ArrayList<>();
        List<Pago> pagos = new ArrayList<>();
        List<SolicitudMantenimiento> tickets = new ArrayList<>();

        boolean run = true;

        while (run) {
            String op = IOUtils.ask("Menú principal",
                    """
                    1) Registrar Propiedad
                    2) Registrar Inquilino
                    3) Crear Contrato de Alquiler
                    4) Registrar Pago
                    5) Registrar Solicitud de Mantenimiento
                    6) Ver Resumen (GestorReportes)
                    0) Salir
                    """);

            if (op == null) break;

            switch (op.trim()) {
                case "1" -> Propiedad.registrarPropiedad(propiedades);
                case "2" -> Inquilino.registrarInquilino(inquilinos);
                case "3" -> ContratoAlquiler.crearContrato(propiedades, inquilinos, contratos);
                case "4" -> Pago.registrarPago(contratos, pagos);
                case "5" -> SolicitudMantenimiento.registrarSolicitud(propiedades, inquilinos, tickets);
                case "6" -> GestorReportes.mostrarResumen(propiedades, inquilinos, contratos, pagos, tickets);
                case "0" -> run = false;
                default -> IOUtils.warn("Opción inválida", "Por favor elija una opción válida.");
            }
        }

        IOUtils.info("Salida", "Gracias por usar el sistema.");
    }
}
