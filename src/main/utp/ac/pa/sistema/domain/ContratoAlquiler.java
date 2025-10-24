package utp.ac.pa.sistema.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContratoAlquiler {
    public enum Estado { ACTIVO, FINALIZADO, CANCELADO }
    private String id;
    private Propiedad propiedad;
    private Inquilino inquilino;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double montoMensual;
    private Estado estado = Estado.ACTIVO;
    private List<Pago> pagos = new ArrayList<>();

    public ContratoAlquiler(String id, Propiedad propiedad, Inquilino inquilino, LocalDate fechaInicio, LocalDate fechaFin, double montoMensual) {
        this.id = id;
        this.propiedad = propiedad;
        this.inquilino = inquilino;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.montoMensual = montoMensual;
    }

    public void registrarPago(Pago p){ pagos.add(p); }
    public boolean estaVigente(LocalDate hoy){
        return (estado == Estado.ACTIVO) && !hoy.isAfter(fechaFin);
    }
}
