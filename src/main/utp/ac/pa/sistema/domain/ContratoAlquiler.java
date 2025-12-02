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

    private String id;
    private Propiedad propiedad;
    private Inquilino inquilino;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double montoMensual;
    private Estado estado;
    private List<Pago> pagos = new ArrayList<>();

    public ContratoAlquiler(String id,
                            Propiedad propiedad,
                            Inquilino inquilino,
                            LocalDate fechaInicio,
                            LocalDate fechaFin,
                            double montoMensual) {
        this.id = id;
        this.propiedad = propiedad;
        this.inquilino = inquilino;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.montoMensual = montoMensual;
        setEstadoInterno(Estado.ACTIVO);
    }

    private void setEstadoInterno(Estado nuevoEstado) {
        this.estado = nuevoEstado;
        if (propiedad != null) {
            if (nuevoEstado == Estado.ACTIVO) {
                propiedad.setEstado(Propiedad.Estado.ALQUILADA);
            } else {
                propiedad.setEstado(Propiedad.Estado.DISPONIBLE);
            }
        }
    }

    public String getId() {
        return id;
    }

    public Propiedad getPropiedad() {
        return propiedad;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public double getMontoMensual() {
        return montoMensual;
    }

    public Estado getEstado() {
        return estado;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void registrarPago(Pago pago) {
        if (pago != null) {
            pagos.add(pago);
        }
    }

    public void finalizar() {
        setEstadoInterno(Estado.FINALIZADO);
    }

    public void cancelar() {
        setEstadoInterno(Estado.CANCELADO);
    }

    public static ContratoAlquiler buscarPorId(List<ContratoAlquiler> contratos, String id) {
        for (ContratoAlquiler c : contratos) {
            if (c.id.equals(id)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Crea un contrato con validaciones (incluye fechas).
     */
    public static void crearContrato(List<Propiedad> propiedades,
                                     List<Inquilino> inquilinos,
                                     List<ContratoAlquiler> contratos) {

        if (propiedades.isEmpty() || inquilinos.isEmpty()) {
            IOUtils.warn("Datos insuficientes",
                    "Debe existir al menos una propiedad y un inquilino antes de crear un contrato.");
            return;
        }

        // ID único
        String id;
        while (true) {
            id = IOUtils.askNonEmpty("Contrato", "ID del contrato:");
            if (buscarPorId(contratos, id) != null) {
                IOUtils.warn("ID duplicado",
                        "Ya existe un contrato con ese ID. Ingrese uno diferente.");
            } else {
                break;
            }
        }

        // Propiedad
        String idProp = IOUtils.askNonEmpty("Contrato", "ID de la propiedad asociada:");
        Propiedad prop = Propiedad.buscarPorId(propiedades, idProp);
        if (prop == null) {
            IOUtils.warn("Propiedad no encontrada", "No existe una propiedad con ese ID.");
            return;
        }
        if (prop.getEstado() == Propiedad.Estado.ALQUILADA) {
            IOUtils.warn("Propiedad ocupada",
                    "La propiedad ya está alquilada. No se puede crear un nuevo contrato activo.");
            return;
        }

        // Inquilino
        String idInq = IOUtils.askNonEmpty("Contrato", "ID del inquilino:");
        Inquilino inq = Inquilino.buscarPorId(inquilinos, idInq);
        if (inq == null) {
            IOUtils.warn("Inquilino no encontrado", "No existe un inquilino con ese ID.");
            return;
        }

        // Fechas
        LocalDate inicio = IOUtils.askFecha("Contrato", "Fecha de inicio");
        LocalDate fin;
        while (true) {
            fin = IOUtils.askFecha("Contrato", "Fecha de fin");
            if (fin.isBefore(inicio)) {
                IOUtils.warn("Rango de fechas inválido",
                        "La fecha de fin no puede ser anterior a la fecha de inicio.");
            } else {
                break;
            }
        }

        // Monto
        double monto = IOUtils.askDouble("Contrato", "Monto mensual del alquiler:");
        if (monto <= 0) {
            IOUtils.warn("Monto inválido", "El monto debe ser mayor que 0.");
            return;
        }

        ContratoAlquiler c = new ContratoAlquiler(id, prop, inq, inicio, fin, monto);
        contratos.add(c);

        IOUtils.info("OK", "Contrato creado:\n" + c);
    }

    /**
     * Permite cambiar manualmente el estado de un contrato.
     */
    public static void cambiarEstadoManual(List<ContratoAlquiler> contratos) {
        if (contratos.isEmpty()) {
            IOUtils.warn("Sin contratos", "No hay contratos registrados.");
            return;
        }

        String id = IOUtils.askNonEmpty("Contrato", "ID del contrato a actualizar:");
        ContratoAlquiler c = buscarPorId(contratos, id);
        if (c == null) {
            IOUtils.warn("No encontrado", "No existe contrato con ese ID.");
            return;
        }

        String msg = "Contrato: " + c.id +
                "\nPropiedad: " + c.propiedad.getId() +
                "\nInquilino: " + c.inquilino.getNombreUsuario() +
                "\nEstado actual: " + c.estado +
                "\n\nSeleccione nuevo estado:\n" +
                "1. ACTIVO\n" +
                "2. FINALIZADO\n" +
                "3. CANCELADO";

        int opcion = IOUtils.askInt("Cambio de estado", msg);
        switch (opcion) {
            case 1 -> c.setEstadoInterno(Estado.ACTIVO);
            case 2 -> c.finalizar();
            case 3 -> c.cancelar();
            default -> {
                IOUtils.warn("Opción inválida", "No se realizó ningún cambio.");
                return;
            }
        }

        IOUtils.info("Estado actualizado",
                "Nuevo estado del contrato " + c.id + ": " + c.estado +
                        "\nEstado de la propiedad " + c.propiedad.getId() +
                        ": " + c.propiedad.getEstado());
    }

    @Override
    public String toString() {
        return "Contrato{" + id + ", prop=" + propiedad.getId() +
                ", inq=" + inquilino.getNombreUsuario() +
                ", estado=" + estado + "}";
    }
}
