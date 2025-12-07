package utp.ac.pa.sistema;

import utp.ac.pa.sistema.domain.ContratoAlquiler;
import utp.ac.pa.sistema.domain.GestorReportes;
import utp.ac.pa.sistema.domain.Inquilino;
import utp.ac.pa.sistema.domain.Pago;
import utp.ac.pa.sistema.domain.Propiedad;
import utp.ac.pa.sistema.domain.SolicitudMantenimiento;
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

            String menu = """
                    === SISTEMA DE GESTION DE ALQUILERES ===
                    
                    1. Registrar propiedad
                    2. Registrar inquilino
                    3. Crear contrato de alquiler
                    4. Registrar pago
                    5. Registrar solicitud de mantenimiento
                    6. Ver resumen del sistema
                    7. Cambiar estado de contrato
                    8. Cambiar estado de ticket de mantenimiento
                    9. Listar propiedades disponibles
                    10. Listar inquilinos
                    0. Salir
                    
                    Seleccione una opcion:
                    """;

            String opcion = IOUtils.ask("Menu principal", menu);

            switch (opcion) {
                case "1" -> Propiedad.registrarPropiedad(propiedades);
                case "2" -> Inquilino.registrarInquilino(inquilinos);
                case "3" -> ContratoAlquiler.crearContrato(propiedades, inquilinos, contratos);
                case "4" -> Pago.registrarPago(contratos, pagos);
                case "5" -> SolicitudMantenimiento.registrarSolicitud(propiedades, inquilinos, tickets);

                case "6" -> GestorReportes.mostrarResumen(
                        propiedades, inquilinos, contratos, pagos, tickets);

                case "7" -> ContratoAlquiler.cambiarEstadoManual(contratos);
                case "8" -> SolicitudMantenimiento.cambiarEstadoInteractivo(tickets);

                case "9" -> Propiedad.listarDisponibles(propiedades);
                case "10" -> Inquilino.listarInquilinos(inquilinos);

                case "0" -> run = false;

                default -> IOUtils.warn("Opcion invalida", "Por favor elija una opcion valida.");
            }
        }

        IOUtils.info("Salida", "Gracias por usar el sistema.");
    }
}
