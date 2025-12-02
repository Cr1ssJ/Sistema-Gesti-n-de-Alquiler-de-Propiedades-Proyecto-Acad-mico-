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

            String menu = """
                    === SISTEMA DE GESTIÓN DE ALQUILERES ===
                    
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
                    
                    Seleccione una opción:
                    """;

            String opcion = IOUtils.ask("Menú principal", menu);

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

                default -> IOUtils.warn("Opción inválida", "Por favor elija una opción válida.");
            }
        }

        IOUtils.info("Salida", "Gracias por usar el sistema.");
    }
}
