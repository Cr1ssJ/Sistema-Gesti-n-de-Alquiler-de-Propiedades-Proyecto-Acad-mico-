package utp.ac.pa.sistema.domain;

import java.time.LocalDate;

/**
 * Plan de pagos asociado a un contrato de alquiler,
 * utilizado para controlar día de corte y próximo vencimiento.
 */
public class PlanPago {

    public enum Estado { PENDIENTE, VENCIDO, PAUSADO }

    private String contratoId;
    private LocalDate proximoVencimiento;
    private int diaCorte;
    private Estado estado;

    public PlanPago(String contratoId, int diaCorte) {
        this.contratoId = contratoId;
        this.diaCorte = diaCorte;
        this.estado = Estado.PENDIENTE;
    }

    public String getContratoId() { 
        return contratoId;
     }

    public LocalDate getProximoVencimiento() { 
        return proximoVencimiento;
     }

    public int getDiaCorte() { 
        return diaCorte;
     }

    public Estado getEstado() { 
        return estado;
     }

    /**
     * Define la próxima fecha de vencimiento del plan.
     */
    public void programar(LocalDate proximaFecha) {
        this.proximoVencimiento = proximaFecha;
    }

    public void marcarVencido() {
        this.estado = Estado.VENCIDO;
    }

    public void pausar() {
        this.estado = Estado.PAUSADO;
    }

    @Override
    public String toString() {
        return "PlanPago{contrato=" + contratoId +
                ", vence=" + proximoVencimiento +
                ", diaCorte=" + diaCorte +
                ", estado=" + estado + "}";
    }
}
