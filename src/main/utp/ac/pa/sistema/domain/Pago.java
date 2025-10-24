package utp.ac.pa.sistema.domain;

import java.time.LocalDate;

public class Pago {
    private String id;
    private String contratoId;
    private LocalDate fechaPago;
    private double monto;
    private String metodo; // transferencia, efectivo, etc.

    public Pago(String id, String contratoId, LocalDate fechaPago, double monto, String metodo) {
        this.id = id;
        this.contratoId = contratoId;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.metodo = metodo;
    }
}
