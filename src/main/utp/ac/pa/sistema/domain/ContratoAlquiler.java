package utp.ac.pa.sistema.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import utp.ac.pa.sistema.utils.IOUtils;

/**
 * Representa un contrato de alquiler entre un inquilino y una propiedad.
 * Aquí vive la lógica de pagos, vencimientos y estado del contrato.
 */
public class ContratoAlquiler {

    private String idContrato;
    private Propiedad propiedad;
    private Inquilino inquilino;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double montoMensual;
    private int diaCorte;
    private LocalDate proximoVencimiento;
    private EstadoContrato estado;
    private List<Pago> pagos;

    public ContratoAlquiler(String idContrato, Propiedad propiedad, Inquilino inquilino,
                            LocalDate fechaInicio, LocalDate fechaFin, double montoMensual,
                            int diaCorte) throws ValidacionException {
        setIdContrato(idContrato);
        setPropiedad(propiedad);
        setInquilino(inquilino);
        setFechaInicio(fechaInicio);
        setFechaFin(fechaFin);
        setMontoMensual(montoMensual);
        setDiaCorte(diaCorte);
        this.estado = EstadoContrato.VIGENTE;
        this.pagos = new ArrayList<>();
        this.proximoVencimiento = calcularProximoVencimientoInicial();
    }

    private void validarTextoObligatorio(String valor, String campo) throws ValidacionException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException("El campo " + campo + " es obligatorio.");
        }
    }

    public String getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(String idContrato) throws ValidacionException {
        validarTextoObligatorio(idContrato, "id de contrato");
        this.idContrato = idContrato.trim();
    }

    public Propiedad getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(Propiedad propiedad) throws ValidacionException {
        if (propiedad == null) {
            throw new ValidacionException("La propiedad del contrato es obligatoria.");
        }
        this.propiedad = propiedad;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) throws ValidacionException {
        if (inquilino == null) {
            throw new ValidacionException("El inquilino del contrato es obligatorio.");
        }
        this.inquilino = inquilino;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) throws ValidacionException {
        if (fechaInicio == null) {
            throw new ValidacionException("La fecha de inicio es obligatoria.");
        }
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) throws ValidacionException {
        if (fechaFin == null || (fechaInicio != null && fechaFin.isBefore(fechaInicio))) {
            throw new ValidacionException("La fecha de fin es obligatoria y debe ser posterior a la fecha de inicio.");
        }
        this.fechaFin = fechaFin;
    }

    public double getMontoMensual() {
        return montoMensual;
    }

    public void setMontoMensual(double montoMensual) throws ValidacionException {
        if (montoMensual <= 0) {
            throw new ValidacionException("El monto mensual debe ser mayor a cero.");
        }
        this.montoMensual = montoMensual;
    }

    public int getDiaCorte() {
        return diaCorte;
    }

    public void setDiaCorte(int diaCorte) throws ValidacionException {
        if (diaCorte < 1 || diaCorte > 28) {
            throw new ValidacionException("El día de corte debe estar entre 1 y 28.");
        }
        this.diaCorte = diaCorte;
    }

    public LocalDate getProximoVencimiento() {
        return proximoVencimiento;
    }

    public EstadoContrato getEstado() {
        return estado;
    }

    public void setEstado(EstadoContrato estado) {
        this.estado = estado;
    }

    public List<Pago> getPagos() {
        return new ArrayList<>(pagos);
    }

    /**
     * Calcula el primer vencimiento del contrato usando fechaInicio y diaCorte.
     */
    private LocalDate calcularProximoVencimientoInicial() {
        LocalDate base = fechaInicio.withDayOfMonth(Math.min(diaCorte, fechaInicio.lengthOfMonth()));
        if (base.isBefore(fechaInicio)) {
            return base.plusMonths(1);
        }
        return base;
    }

    /**
     * Avanza el próximo vencimiento un mes.
     * Se llama normalmente cuando se registra un pago correcto.
     */
    public void actualizarProximoVencimiento() {
        this.proximoVencimiento = this.proximoVencimiento.plusMonths(1);
    }

    /**
     * Indica si el contrato está vencido respecto al próximo vencimiento.
     */
    public boolean estaVencido() {
        return LocalDate.now().isAfter(proximoVencimiento) && estado == EstadoContrato.VIGENTE;
    }

    /**
     * Registra un pago dentro del contrato y actualiza el vencimiento.
     */
    public void registrarPago(Pago pago) throws ValidacionException {
        if (pago == null) {
            throw new ValidacionException("El pago no puede ser nulo.");
        }
        this.pagos.add(pago);
        actualizarProximoVencimiento();
    }

    /**
     * Flujo interactivo para crear un contrato.
     */
    public static void crearContrato(List<Propiedad> propiedades,
                                     List<Inquilino> inquilinos,
                                     List<ContratoAlquiler> contratos) {
        if (propiedades.isEmpty()) {
            IOUtils.warn("Sin propiedades", "Debe registrar una propiedad primero.");
            return;
        }
        if (inquilinos.isEmpty()) {
            IOUtils.warn("Sin inquilinos", "Debe registrar un inquilino primero.");
            return;
        }
        try {
            String id = IOUtils.askNonEmpty("Crear contrato", "ID del contrato");
            if (contratos.stream().anyMatch(c -> c.getIdContrato().equalsIgnoreCase(id))) {
                IOUtils.warn("Duplicado", "Ya existe un contrato con ese ID.");
                return;
            }

            String idProp = IOUtils.askNonEmpty("Crear contrato", "ID de la propiedad");
            Propiedad prop = propiedades.stream()
                    .filter(p -> p.getIdPropiedad().equalsIgnoreCase(idProp))
                    .findFirst()
                    .orElse(null);
            if (prop == null) {
                IOUtils.warn("No encontrada", "Propiedad no encontrada.");
                return;
            }
            if (!prop.isDisponible()) {
                IOUtils.warn("No disponible", "La propiedad ya esta alquilada.");
                return;
            }

            String idInq = IOUtils.askNonEmpty("Crear contrato", "ID del inquilino");
            Inquilino inq = inquilinos.stream()
                    .filter(i -> i.getId().equalsIgnoreCase(idInq))
                    .findFirst()
                    .orElse(null);
            if (inq == null) {
                IOUtils.warn("No encontrado", "Inquilino no encontrado.");
                return;
            }

            LocalDate inicio = IOUtils.askFecha("Crear contrato", "Fecha de inicio");
            LocalDate fin = IOUtils.askFecha("Crear contrato", "Fecha de fin");
            double monto = IOUtils.askDouble("Crear contrato",
                    "Monto mensual (sugerido: " + prop.calcularPrecioTotalMensual() + ")");

            int diaCorte;
            while (true) {
                diaCorte = IOUtils.askInt("Crear contrato", "Dia de corte (1-28)");
                if (diaCorte >= 1 && diaCorte <= 28) break;
                IOUtils.warn("Dato invalido", "El dia de corte debe estar entre 1 y 28.");
            }

            ContratoAlquiler contrato = new ContratoAlquiler(
                    id, prop, inq, inicio, fin, monto, diaCorte);
            contratos.add(contrato);
            prop.setDisponible(false);
            IOUtils.info("OK", "Contrato creado y propiedad marcada como no disponible.");
        } catch (ValidacionException e) {
            IOUtils.warn("Error", e.getMessage());
        }
    }

    /**
     * Permite cambiar el estado de un contrato manualmente.
     */
    public static void cambiarEstadoManual(List<ContratoAlquiler> contratos) {
        if (contratos.isEmpty()) {
            IOUtils.info("Contratos", "No hay contratos registrados.");
            return;
        }
        String id = IOUtils.askNonEmpty("Cambiar estado", "ID de contrato");
        ContratoAlquiler contrato = contratos.stream()
                .filter(c -> c.getIdContrato().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
        if (contrato == null) {
            IOUtils.warn("No encontrado", "Contrato no encontrado.");
            return;
        }
        String opcion = IOUtils.askNonEmpty("Nuevo estado",
                "Seleccione estado (1=VIGENTE, 2=VENCIDO, 3=CANCELADO)");
        switch (opcion) {
            case "1" -> contrato.setEstado(EstadoContrato.VIGENTE);
            case "2" -> contrato.setEstado(EstadoContrato.VENCIDO);
            case "3" -> {
                contrato.setEstado(EstadoContrato.CANCELADO);
                contrato.getPropiedad().setDisponible(true);
            }
            default -> {
                IOUtils.warn("Opcion invalida", "No se realizo ningun cambio.");
                return;
            }
        }
        IOUtils.info("OK", "Estado actualizado a " + contrato.getEstado());
    }
}
