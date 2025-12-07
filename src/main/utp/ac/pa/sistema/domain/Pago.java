package utp.ac.pa.sistema.domain;

import java.time.LocalDate;
import java.util.List;

import utp.ac.pa.sistema.utils.IOUtils;

/**
 * Representa un pago realizado por un inquilino asociado a un contrato.
 */
public class Pago {

    private String idPago;
    private ContratoAlquiler contrato;
    private LocalDate fechaPago;
    private double monto;
    private String metodoPago;
    private String referencia;
    private boolean aTiempo;

    public Pago(String idPago, ContratoAlquiler contrato, LocalDate fechaPago,
                double monto, String metodoPago, String referencia)
            throws ValidacionException {
        setIdPago(idPago);
        setContrato(contrato);
        setFechaPago(fechaPago);
        setMonto(monto);
        setMetodoPago(metodoPago);
        setReferencia(referencia);
        // Se calcula si el pago fue a tiempo en base al proximo vencimiento del contrato
        this.aTiempo = !fechaPago.isAfter(contrato.getProximoVencimiento());
    }

    private void validarTextoObligatorio(String valor, String campo) throws ValidacionException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException("El campo " + campo + " es obligatorio.");
        }
    }

    public String getIdPago() {
        return idPago;
    }

    public void setIdPago(String idPago) throws ValidacionException {
        validarTextoObligatorio(idPago, "id de pago");
        this.idPago = idPago.trim();
    }

    public ContratoAlquiler getContrato() {
        return contrato;
    }

    public void setContrato(ContratoAlquiler contrato) throws ValidacionException {
        if (contrato == null) {
            throw new ValidacionException("El contrato asociado al pago es obligatorio.");
        }
        this.contrato = contrato;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) throws ValidacionException {
        if (fechaPago == null) {
            throw new ValidacionException("La fecha de pago es obligatoria.");
        }
        this.fechaPago = fechaPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) throws ValidacionException {
        if (monto <= 0) {
            throw new ValidacionException("El monto del pago debe ser mayor a cero.");
        }
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) throws ValidacionException {
        validarTextoObligatorio(metodoPago, "metodo de pago");
        this.metodoPago = metodoPago.trim();
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) throws ValidacionException {
        validarTextoObligatorio(referencia, "referencia");
        this.referencia = referencia.trim();
    }

    public boolean isATiempo() {
        return aTiempo;
    }

    /**
     * Flujo interactivo para registrar un pago.
     */
    public static void registrarPago(List<ContratoAlquiler> contratos, List<Pago> pagos) {
        if (contratos.isEmpty()) {
            IOUtils.warn("Sin contratos", "Debe crear un contrato antes de registrar pagos.");
            return;
        }
        try {
            String contratoId = IOUtils.askNonEmpty("Registrar pago", "ID del contrato");
            ContratoAlquiler contrato = contratos.stream()
                    .filter(c -> c.getIdContrato().equalsIgnoreCase(contratoId))
                    .findFirst()
                    .orElse(null);
            if (contrato == null) {
                IOUtils.warn("No encontrado", "Contrato no encontrado.");
                return;
            }

            String idPago = IOUtils.askNonEmpty("Registrar pago", "ID del pago");
            if (pagos.stream().anyMatch(p -> p.getIdPago().equalsIgnoreCase(idPago))) {
                IOUtils.warn("Duplicado", "Ya existe un pago con ese ID.");
                return;
            }

            LocalDate fecha = IOUtils.askFecha("Registrar pago", "Fecha del pago");
            double monto = IOUtils.askDouble("Registrar pago", "Monto pagado");
            String metodo = IOUtils.askNonEmpty("Registrar pago", "Metodo de pago");
            String referencia = IOUtils.askNonEmpty("Registrar pago", "Referencia");

            Pago pago = new Pago(idPago, contrato, fecha, monto, metodo, referencia);
            pagos.add(pago);
            contrato.registrarPago(pago);

            String mensaje = pago.isATiempo() ? "Pago a tiempo." : "Pago fuera de fecha.";
            IOUtils.info("Pago registrado", mensaje);
        } catch (ValidacionException e) {
            IOUtils.warn("Error", e.getMessage());
        }
    }
}
