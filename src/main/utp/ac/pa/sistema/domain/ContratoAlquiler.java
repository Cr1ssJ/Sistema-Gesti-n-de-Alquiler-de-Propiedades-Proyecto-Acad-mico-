package utp.ac.pa.sistema.domain;

import utp.ac.pa.sistema.utils.IOUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un contrato de alquiler entre una propiedad y un inquilino.
 */
public class ContratoAlquiler {

    public enum Estado { ACTIVO, FINALIZADO, CANCELADO }

    String id;
    Propiedad propiedad;
    Inquilino inquilino;
    LocalDate fechaInicio;
    LocalDate fechaFin;
    double montoMensual;
    Estado estado = Estado.ACTIVO;
    List<Pago> pagos = new ArrayList<>();

    public ContratoAlquiler(String id, Propiedad propiedad, Inquilino inquilino,
                            LocalDate fechaInicio, LocalDate fechaFin, double montoMensual) {
        this.id = id;
        this.propiedad = propiedad;
        this.inquilino = inquilino;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.montoMensual = montoMensual;
    }

    public void registrarPago(Pago p) {
        pagos.add(p);
    }

    public boolean estaVigente(LocalDate hoy) {
        return (estado == Estado.ACTIVO) && !hoy.isAfter(fechaFin);
    }

    public String getId() { 
        return id; 
    }

    /**
     * Caso de uso: crear un nuevo contrato.
     * Utiliza las listas de propiedades e inquilinos existentes.
     */
    public static void crearContrato(List<Propiedad> propiedades,
                                     List<Inquilino> inquilinos,
                                     List<ContratoAlquiler> contratos) {

        if (propiedades.isEmpty() || inquilinos.isEmpty()) {
            IOUtils.warn("Faltan datos",
                    "Debe existir al menos una propiedad y un inquilino.");
            return;
        }

        String id = IOUtils.ask("Contrato", "ID del contrato:");
        String propId = IOUtils.ask("Contrato", "ID de la propiedad:");
        Propiedad prop = propiedades.stream()
                .filter(p -> p.getId().equals(propId))
                .findFirst()
                .orElse(null);

        if (prop == null) {
            IOUtils.warn("Error", "Propiedad no encontrada.");
            return;
        }

        String inqId = IOUtils.ask("Contrato", "ID del inquilino:");
        Inquilino inq = inquilinos.stream()
                .filter(i -> i.id.equals(inqId))
                .findFirst()
                .orElse(null);

        if (inq == null) {
            IOUtils.warn("Error", "Inquilino no encontrado.");
            return;
        }

        String fi = IOUtils.ask("Contrato", "Fecha inicio (YYYY-MM-DD):");
        String ff = IOUtils.ask("Contrato", "Fecha fin (YYYY-MM-DD):");
        double monto = IOUtils.toDouble(
                IOUtils.ask("Contrato", "Monto mensual:"), 0);

        ContratoAlquiler c = new ContratoAlquiler(
                id, prop, inq,
                LocalDate.parse(fi),
                LocalDate.parse(ff),
                monto
        );
        contratos.add(c);

        IOUtils.info("OK", "Contrato creado:\n" + c);
    }

    @Override
    public String toString() {
        return "Contrato{" + id + ", prop=" + propiedad.getId() +
                ", inq=" + inquilino.getNombreUsuario() + "}";
    }
}
