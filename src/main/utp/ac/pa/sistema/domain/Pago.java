package utp.ac.pa.sistema.domain;

import utp.ac.pa.sistema.utils.IOUtils;
import java.time.LocalDate;
import java.util.List;

/**
 * Representa un pago asociado a un contrato de alquiler.
 */
public class Pago {

    private String id;
    private String contratoId;
    private LocalDate fechaPago;
    private double monto;
    private String metodo;

    public Pago(String id, String contratoId, LocalDate fechaPago,
                double monto, String metodo) {
        this.id = id;
        this.contratoId = contratoId;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.metodo = metodo;
    }

    public String getId() {
        return id;
    }

    public String getContratoId() {
        return contratoId;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public double getMonto() {
        return monto;
    }

    public String getMetodo() {
        return metodo;
    }

    /**
     * Registra un pago validando:
     * - Que exista el contrato
     * - Que el ID de pago no se repita
     * - Que el monto sea numérico y > 0
     * - Que la fecha sea válida
     */
    public static void registrarPago(List<ContratoAlquiler> contratos, List<Pago> pagos) {

        if (contratos.isEmpty()) {
            IOUtils.warn("Sin contratos",
                    "Debe existir al menos un contrato para registrar pagos.");
            return;
        }

        // Verificar que el contrato exista
        String contratoId = IOUtils.askNonEmpty("Pago", "ID del contrato:");
        ContratoAlquiler c = ContratoAlquiler.buscarPorId(contratos, contratoId);
        if (c == null) {
            IOUtils.warn("Contrato no encontrado",
                    "No existe un contrato con ese ID.");
            return;
        }

        // ID de pago único
        String id;
        while (true) {
            id = IOUtils.askNonEmpty("Pago", "ID del pago:");
            boolean existe = false;
            for (Pago p : pagos) {
                if (p.id.equals(id)) {
                    existe = true;
                    break;
                }
            }
            if (existe) {
                IOUtils.warn("ID duplicado",
                        "Ya existe un pago con ese ID. Ingrese otro.");
            } else {
                break;
            }
        }

        // Fecha de pago
        LocalDate fecha = IOUtils.askFecha("Pago", "Fecha de pago");

        // Monto (solo números, mayor que 0)
        double monto = IOUtils.askDouble("Pago", "Monto del pago:");
        if (monto <= 0) {
            IOUtils.warn("Monto inválido", "El monto debe ser mayor que 0.");
            return;
        }

        String metodo = IOUtils.askNonEmpty("Pago",
                "Método de pago (efectivo, transferencia, etc.):");

        Pago p = new Pago(id, contratoId, fecha, monto, metodo);
        c.registrarPago(p);
        pagos.add(p);

        IOUtils.info("OK", "Pago registrado:\n" + p);
    }

    @Override
    public String toString() {
        return "Pago{" + id + ", $" + monto + ", " + fechaPago + ", " + metodo + "}";
    }
}
