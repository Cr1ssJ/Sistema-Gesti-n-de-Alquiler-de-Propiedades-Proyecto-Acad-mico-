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

    /**
     * Caso de uso: registrar un pago para un contrato.
     */
    public static void registrarPago(List<ContratoAlquiler> contratos,
                                     List<Pago> pagos) {
        if (contratos.isEmpty()) {
            IOUtils.warn("Faltan datos", "Debe existir al menos un contrato.");
            return;
        }

        String contratoId = IOUtils.ask("Pago", "ID del contrato:");
        ContratoAlquiler c = contratos.stream()
                .filter(k -> k.getId().equals(contratoId))
                .findFirst()
                .orElse(null);

        if (c == null) {
            IOUtils.warn("Error", "Contrato no encontrado.");
            return;
        }

        String id = IOUtils.ask("Pago", "ID del pago:");
        String fecha = IOUtils.ask("Pago", "Fecha (YYYY-MM-DD):");
        double monto = IOUtils.toDouble(IOUtils.ask("Pago", "Monto:"), 0);
        String metodo = IOUtils.ask("Pago", "Método:");

        Pago p = new Pago(id, contratoId, LocalDate.parse(fecha), monto, metodo);
        c.registrarPago(p);
        pagos.add(p);

        IOUtils.info("OK", "Pago registrado:\n" + p);
    }

    @Override
    public String toString() {
        return "Pago{" + id + ", $" + monto + ", " + fechaPago + ", " + metodo + "}";
    }
}
